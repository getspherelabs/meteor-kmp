package io.spherelabs.meteor.extension

import io.spherelabs.meteor.configs.Change
import io.spherelabs.meteor.reducer.Reducer

@Deprecated(
    "This method is deprecated. Use expect(dslBuilder: ChangeBuilder<State, Effect>.() -> Unit) instead.",
    ReplaceWith(
        "change {}",
        "io.spherelabs.meteor.extension"
    )
)
public fun <State : Any, Wish : Any, Effect : Any> Reducer<State, Wish, Effect>.expect(
    action: () -> State
): Change<State, Effect> {
    return Change(state = action())
}

@Deprecated(
    "This method is deprecated. Use expect(dslBuilder: ChangeBuilder<State, Effect>.() -> Unit) instead.",
    ReplaceWith(
        "change {}",
        "io.spherelabs.meteor.extension"
    )
)
public fun <State : Any, Wish : Any, Effect : Any> Reducer<State, Wish, Effect>.expect(
    effectAction: () -> Effect,
    stateAction: () -> State
): Change<State, Effect> {
    return Change(effect = effectAction(), state = stateAction())
}

public fun <State : Any, Wish : Any, Effect : Any> Reducer<State, Wish, Effect>.effect(action: () -> Effect): Change<State, Effect> {
    return Change(effect = action())
}

public fun <State : Any, Wish : Any, Effect : Any> Reducer<State, Wish, Effect>.change(
    dslBuilder: ChangeDslBuilder<State, Effect>.() -> Unit
): Change<State, Effect> {
    val newBuilder = ChangeDslBuilder<State, Effect>()
    newBuilder.dslBuilder()
    return newBuilder.build()
}

public fun <State : Any, Wish : Any, Effect : Any> Reducer<State, Wish, Effect>.unexpected(action: () -> State): Change<State, Effect> {
    return Change(null, state = action())
}

public fun <State : Any, Wish : Any, Effect : Any> Reducer<State, Wish, Effect>.route(action: () -> Effect): Change<State, Effect> {
    return Change(effect = action())
}
