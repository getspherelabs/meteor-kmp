package io.spherelabs.meteor.reducer

import io.spherelabs.meteor.configs.Change

/**
 * A  [Reducer] are responsible for taking the current state and an wish,
 * and producing new state and new effect based on the given inputs.
 */
fun interface Reducer<State : Any, Wish : Any, Effect : Any> {
    fun reduce(state: State, wish: Wish): Change<State, Effect>
}

public fun <State : Any, Wish : Any, Effect : Any> Reducer<State, Wish, Effect>.expect(
    action: () -> State
): Change<State, Effect> {
    return Change(state = action())
}

public fun <State : Any, Wish : Any, Effect : Any> Reducer<State, Wish, Effect>.unexpected(): Change<State, Effect> {
    return Change(null, null)
}

public fun <State : Any, Wish : Any, Effect : Any> Reducer<State, Wish, Effect>.effect(action: () -> Effect): Change<State, Effect> {
    return Change(effect = action())
}

public fun <State : Any, Wish : Any, Effect : Any> Reducer<State, Wish, Effect>.route(action: () -> Effect): Change<State, Effect> {
    return Change(effect = action())
}
