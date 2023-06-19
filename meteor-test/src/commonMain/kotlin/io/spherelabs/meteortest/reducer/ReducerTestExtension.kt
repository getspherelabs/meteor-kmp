package io.spherelabs.meteortest.reducer

import io.spherelabs.meteor.exception.NotInitializedException
import io.spherelabs.meteor.reducer.Reducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * A [runReducerTest] runs a test for a [Reducer].
 * It takes the initial state, a wish (action/intent) and [CoroutineScope] for collecting state updates.
 * Also, the action callback helps us to handle the resulting state and effect.
 *
 */
public fun <State : Any, Wish : Any, Effect : Any> Reducer<State, Wish, Effect>.runReducerTest(
    initialState: State,
    wish: Wish,
    scope: CoroutineScope,
    action: (State, Effect) -> Unit
) {
    val internalState = MutableStateFlow(initialState)
    val state = internalState.asStateFlow()

    val (newEffect, newState) = reduce(initialState, wish)

    internalState.value = checkNotNull(newState) {
        throw NotInitializedException()
    }

    scope.launch {
        state.collect {
            println("Updated state is $it")
        }
    }

    action(state.value, checkNotNull(newEffect))
}

public fun <State : Any, Wish : Any, Effect : Any> Reducer<State, Wish, Effect>.runReducerTestWithState(
    initialState: State,
    wish: Wish,
    scope: CoroutineScope,
    action: (State) -> Unit
) {
    val internalState = MutableStateFlow(initialState)
    val state = internalState.asStateFlow()

    val (newEffect, newState) = reduce(initialState, wish)

    internalState.value = checkNotNull(newState) {
        throw NotInitializedException()
    }

    scope.launch {
        state.collect {
            println("Updated state is $it")
        }
    }

    action(state.value)
}

public fun <State : Any, Wish : Any, Effect : Any> Reducer<State, Wish, Effect>.runReducerTestWithEffect(
    initialState: State,
    wish: Wish,
    scope: CoroutineScope,
    action: (Effect) -> Unit
) {
    var updatedEffect: Effect? = null
    val internalEffect: Channel<Effect> = Channel()
    val effect = internalEffect.receiveAsFlow()

    val (newEffect, newState) = reduce(initialState, wish)

    scope.launch {
        internalEffect.send(
            checkNotNull(newEffect) {
                throw NotInitializedException("Effect is not initialized.")
            }
        )
    }

    scope.launch {
        effect.collect {
            updatedEffect = it
        }
    }

    action(
        checkNotNull(updatedEffect) {
            throw NotInitializedException("Effect is not initialized.")
        }
    )
}
