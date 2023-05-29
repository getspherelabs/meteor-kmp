package io.spherelabs.meteor.reducer

/**
 * A  [Reducer] are responsible for taking the current state and an wish,
 * and producing a new state based on the given inputs.
 */
fun interface Reducer<State : Any, Wish : Any> {
    fun reduce(state: State, wish: Wish): State
}
