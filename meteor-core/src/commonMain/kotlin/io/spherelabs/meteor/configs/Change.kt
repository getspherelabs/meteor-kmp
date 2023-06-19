package io.spherelabs.meteor.configs

public data class Change<State : Any, Effect : Any>(
    val effect: Effect? = null,
    val state: State? = null
)
