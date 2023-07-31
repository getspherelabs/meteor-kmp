package io.spherelabs.meteor.reducer

import io.spherelabs.meteor.configs.Change

/**
 * A  [Reducer] are responsible for taking the current state and an wish,
 * and producing new state and new effect based on the given inputs.
 */
public interface Reducer<State : Any, Wish : Any, Effect : Any> {
    public fun reduce(currentState: State, currentWish: Wish): Change<State, Effect>
}
