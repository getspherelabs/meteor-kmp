package io.spherelabs.meteor.store

import io.spherelabs.meteor.interceptor.Message
import io.spherelabs.meteor.configs.Change
import io.spherelabs.meteor.configs.MeteorConfigs
import io.spherelabs.meteor.interceptor.StateTransition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class MeteorStore<State : Any, Wish : Any, Effect : Any> constructor(
    private val configs: MeteorConfigs<State, Wish, Effect>,
    private val mainScope: CoroutineScope
) : Store<State, Wish, Effect>, MeteorConfigs<State, Wish, Effect> by configs {

    private val _state: MutableStateFlow<State> = MutableStateFlow(configs.initialState)
    override val state: StateFlow<State> = _state.asStateFlow()

    private var _message: Channel<Message<State, Wish, Effect>> = Channel(Channel.BUFFERED)
    private val message: Flow<Message<State, Wish, Effect>> = _message.receiveAsFlow()

    override var currentState: State = configs.initialState

    private val _effect: Channel<Effect> = Channel()
    override val effect: Flow<Effect> = _effect.receiveAsFlow()

    private val lock = Mutex()

    override suspend fun wish(wish: Wish) {
        mainScope.launch {
            _message.send(Message.ReceivedWish(storeName, currentWish = wish))
            lock.withLock {
                handleMessage()
                handleReducer(wish)
                handleMiddleware(wish)
            }
        }
    }

    private suspend fun handleReducer(wish: Wish) {
        val oldState = _state.value
        val newState = applyReducer(oldState, wish)

        newState.state?.let {
            _state.value = it
            currentState = it
            _message.send(Message.OnStateTransition(storeName, currentState, StateTransition.PREVIOUS))
            _message.send(Message.OnStateTransition(storeName, it, StateTransition.NEW))
        }

        newState.effect?.let { newEffect ->
            _message.send(
                Message.NewEffect(storeName, newEffect)
            )
            _message.send(Message.ReceivedEffect(storeName, newEffect))
            mainScope.launch {
                _effect.send(newEffect)
                _message.send(Message.NewEffect(storeName, newEffect))
            }
        }
    }

    private fun handleMessage() {
        mainScope.launch {
            message.collectLatest {
                configs.interceptor.process(it)
            }
        }
    }

    private fun handleMiddleware(wish: Wish) {
        mainScope.launch {
            configs.middlewares.forEach { newMiddleware ->
                newMiddleware.process(
                    state = currentState,
                    wish = wish,
                    next = { newWish ->
                        _message.send(Message.NewWish(storeName, newWish))
                        wish(newWish)
                    }
                )
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
