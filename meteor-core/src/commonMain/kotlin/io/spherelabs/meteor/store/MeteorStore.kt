package io.spherelabs.meteor.store

import io.spherelabs.meteor.middleware.Middleware
import io.spherelabs.meteor.configs.MeteorConfigs
import io.spherelabs.meteor.configs.To
import io.spherelabs.meteor.reducer.Reducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class MeteorStore<State : Any, Wish : Any, Effect : Any>(
    val configs: MeteorConfigs<State>,
    private val reducer: Reducer<State, Wish>,
    private val middleware: Middleware<State, Wish, Effect>,
    private val scope: CoroutineScope
) : Store<State, Wish, Effect> {

    private val _state: MutableStateFlow<State> = MutableStateFlow(configs.initialState)
    override val state: StateFlow<State> = _state.asStateFlow()

    override var currentState: State = configs.initialState

    private val _effect: Channel<Effect> = Channel()
    override val effect: Flow<Effect> = _effect.receiveAsFlow()

    private val lock = Mutex()

    override suspend fun wish(wish: Wish) {
        lock.withLock {
            val oldState = _state.value

            val newState = applyReducer(oldState, wish)

            if (oldState != newState) {
                _state.value = newState
                currentState = newState
            }


            scope.launch {
                middleware.process(
                    effect = To(
                        send = {
                            _effect.send(it)
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
