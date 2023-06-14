package io.spherelabs.meteor.store

import io.spherelabs.meteor.configs.MeteorConfigs
import io.spherelabs.meteor.configs.Change
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


class MeteorStore<State : Any, Wish : Any, Effect : Any>(
    private val configs: MeteorConfigs<State, Wish, Effect>,
    private val mainScope: CoroutineScope
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
                println("New state is $newState")
                println("New wish is $wish")
                newState.state?.let {
                    _state.value = it
                    currentState = it
                    println("Current state is $it")
                }

                println("Debug scope  MS: $mainScope")

                newState.effect?.let { newEffect ->
                    mainScope.launch {
                        _effect.send(newEffect)
                    }
                }
                println("Current new state is ${state.value}")
                mainScope.launch {
                    configs.middleware.process(
                        wish = wish,
                        next = { newWish ->
                            println("New wish: $newWish")
                            wish(newWish)
                        })

                }
            }
    }

    private fun applyReducer(state: State, wish: Wish): Change<State, Effect> {
        return configs.reducer.reduce(state, wish)
    }

    override fun cancel() {
        mainScope.cancel()
    }
}

fun <State : Any, Wish : Any, Effect : Any> createMeteor(
    configs: MeteorConfigs<State, Wish, Effect>,
    mainScope: CoroutineScope
): Store<State, Wish, Effect> {
    return MeteorStore(
        configs = configs,
        mainScope = mainScope
    )
}

fun <State : Any, Wish : Any, Effect : Any> CoroutineScope.createMeteor(
    configs: MeteorConfigs<State, Wish, Effect>,
): Store<State, Wish, Effect> {
    val store =  MeteorStore(
        configs = configs,
        mainScope = this
    )

    coroutineContext.job.invokeOnCompletion {
        store.cancel()
    }

    return store

}


