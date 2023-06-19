package io.spherelabs.meteor

public interface ReducerScope<State : Any, Wish : Any> {
    public fun reducer(state: State, wish: Wish): State
}
