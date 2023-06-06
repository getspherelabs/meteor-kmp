package io.spherelabs.meteor

interface ReducerScope<State : Any, Wish : Any> {
    fun reducer(state: State, wish: Wish): State
}
