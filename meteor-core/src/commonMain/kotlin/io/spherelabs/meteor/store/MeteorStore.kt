package io.spherelabs.meteor.store

import io.spherelabs.meteor.configs.Change
import io.spherelabs.meteor.configs.MeteorConfigs
import io.spherelabs.meteorlogger.log
import io.spherelabs.meteorlogger.logTest
import io.spherelabs.meteorlogger.logTestEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

public class MeteorStore<State : Any, Wish : Any, Effect : Any> constructor(
    private val configs: MeteorConfigs<State, Wish, Effect>,
    private val mainScope: CoroutineScope
) : Store<State, Wish, Effect> {

    private val _state: MutableStateFlow<State> = MutableStateFlow(configs.initialState)
    override val state: StateFlow<State> = _state.asStateFlow()

    override var currentState: State = configs.initialState

    private val _effect: Channel<Effect> = Channel()
    override val effect: Flow<Effect> = _effect.receiveAsFlow()

    private val lock = Mutex()

    init {
        log("Initialized the store ${configs.storeName}")
    }

    override suspend fun wish(wish: Wish) {
        mainScope.launch {
            lock.withLock {
                val oldState = _state.value
                val newState = applyReducer(oldState, wish)

                newState.state?.let {
                    _state.value = it
                    currentState = it
                    logTest(previousState = oldState, newState = it, wish = wish)
                }

                newState.effect?.let { newEffect ->
                    mainScope.launch {
                        _effect.send(newEffect)
                        logTestEffect(newEffect)
                    }
                }

                mainScope.launch {
                    configs.middleware.process(
                        state = currentState,
                        wish = wish,
                        next = { newWish ->
                            logTest(wish = newWish)
                            wish(newWish)
                        }
                    )
                }
            }
        }
    }

    private fun applyReducer(state: State, wish: Wish): Change<State, Effect> {
        return configs.reducer.reduce(state, wish)
    }

    override fun cancel() {
        mainScope.cancel()
    }

    public companion object {
        private const val TAG = "MeteorStore"
    }
}

public fun <State : Any, Wish : Any, Effect : Any> createMeteor(
    configs: MeteorConfigs<State, Wish, Effect>,
    mainScope: CoroutineScope
): Store<State, Wish, Effect> {
    return MeteorStore(
        configs = configs,
        mainScope = mainScope
    )
}

public fun <State : Any, Wish : Any, Effect : Any> CoroutineScope.createMeteor(
    configs: MeteorConfigs<State, Wish, Effect>
): Store<State, Wish, Effect> {
    val store = MeteorStore(
        configs = configs,
        mainScope = this
    )

    coroutineContext.job.invokeOnCompletion {
        store.cancel()
    }

    return store
}
