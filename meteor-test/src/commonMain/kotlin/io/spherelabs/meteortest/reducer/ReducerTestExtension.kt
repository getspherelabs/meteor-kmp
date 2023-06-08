package io.spherelabs.meteortest.reducer

import io.spherelabs.meteor.reducer.Reducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

fun <State : Any, Wish : Any, Effect : Any> Reducer<State, Wish, Effect>.runReducerTest(
    initialState: State,
    wish: Wish,
    scope: CoroutineScope,
    action: (State, Effect) -> Unit
) {
    val internalState = MutableStateFlow(initialState)
    val state = internalState.asStateFlow()

    val (newEffect, newState) = reduce(initialState, wish)

    internalState.value = checkNotNull(newState) {
        "State is not initialized"
    }

    scope.launch {
        state.collect {
            println("Updated state is $it")
        }
    }

    action(state.value, checkNotNull(newEffect))
}


fun <State : Any, Wish : Any, Effect : Any> Reducer<State, Wish, Effect>.runReducerTestWithState(
    initialState: State,
    wish: Wish,
    scope: CoroutineScope,
    action: (State) -> Unit
) {
    val internalState = MutableStateFlow(initialState)
    val state = internalState.asStateFlow()

    val (newEffect, newState) = reduce(initialState, wish)

    internalState.value = checkNotNull(newState) {
        "State is not initialized"
    }

    scope.launch {
        state.collect {
            println("Updated state is $it")
        }
    }

    action(state.value)
}

fun <State : Any, Wish : Any, Effect : Any> Reducer<State, Wish, Effect>.runReducerTestWithEffect(
    initialState: State,
    wish: Wish,
    scope: CoroutineScope,
    action: (Effect) -> Unit
) {
    var updatedEffect: Effect? = null
    val internalEffect: Channel<Effect> = Channel()
    val effect = internalEffect.receiveAsFlow()

    val (newEffect, newState) = reduce(initialState, wish)

    internalEffect.trySend(checkNotNull(newEffect))

    scope.launch {
        effect.collect {
            updatedEffect = it
        }
    }

    action(checkNotNull(updatedEffect))
}
