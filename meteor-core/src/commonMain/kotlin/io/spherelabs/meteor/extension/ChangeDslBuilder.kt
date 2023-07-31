package io.spherelabs.meteor.extension

import io.spherelabs.meteor.configs.Change

public class ChangeDslBuilder<State : Any, Effect : Any> {
    private var effect: Effect? = null
    private var state: State? = null

    public fun effect(effectAction: () -> Effect) {
        effect = effectAction()
    }

    public fun state(stateAction: () -> State) {
        state = stateAction()
    }

    public fun build(): Change<State, Effect> {
        return Change(effect = effect, state = state)
    }
}
