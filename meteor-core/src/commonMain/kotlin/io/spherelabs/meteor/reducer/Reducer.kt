package io.spherelabs.meteor.reducer

import io.spherelabs.meteor.configs.To

/**
 * A  [Reducer] are responsible for taking the current state and an wish,
 * and producing a new state based on the given inputs.
 */
fun interface Reducer<State : Any, Wish : Any, Effect : Any> {
    fun reduce(state: State, wish: Wish): To<State, Effect>
}

public fun <State : Any, Wish : Any, Effect : Any> Reducer<State, Wish, Effect>.expect(
    action: () -> State
): To<State, Effect> {
    return To(state = action())
}

public fun <State : Any, Wish : Any, Effect : Any> Reducer<State, Wish, Effect>.unexpected(): To<State, Effect> {
    return To(null, null)
}
