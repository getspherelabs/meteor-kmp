package io.spherelabs.meteor.store

import io.spherelabs.meteor.Middleware
import io.spherelabs.meteor.configs.MeteorConfigs
import io.spherelabs.meteor.configs.To
import io.spherelabs.meteor.reducer.Reducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class MeteorStore<State : Any, Wish : Any, Effect : Any>(
    private val configs: MeteorConfigs<State>,
    private val reducer: Reducer<State, Wish>,
    private val middleware: Middleware<State, Wish, Effect>,
    private val scope: CoroutineScope
) : Store<State, Wish, Effect> {

    private val _state: MutableStateFlow<State> = MutableStateFlow(configs.initialState)
    override val state: StateFlow<State> = _state.asStateFlow()

    override var currentState: State = configs.initialState

    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow()
    override val effect: Flow<Effect> = _effect.asSharedFlow()

    private val lock = Mutex()

    override suspend fun wish(wish: Wish) {
        lock.withLock {
            withContext(configs.ioDispatcher) {
                val oldState = _state.value

                val newState = applyReducer(oldState, wish)

                if (oldState != newState) {
                    _state.value = newState
                    currentState = newState
                }


                middleware.process(
                    effect = To(
                        send = {
                            _effect.tryEmit(it)
                        },
                        state = currentState
                    ), wish = wish,
                    next = { newWish ->
                        wish(newWish)
                    })
            }

        }
    }

    private fun applyReducer(state: State, wish: Wish): State {
        return reducer.reduce(state, wish)
    }
}

fun <State : Any, Wish : Any, Effect : Any> createMeteor(
    configs: MeteorConfigs<State>,
    reducer: Reducer<State, Wish>,
    middleware: Middleware<State, Wish, Effect>,
    scope: CoroutineScope
): Store<State, Wish, Effect> {
    return MeteorStore(
        configs = configs,
        reducer = reducer,
        middleware = middleware,
        scope = scope
    )
}
