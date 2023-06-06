package io.spherelabs.meteor.configs

data class To<State : Any, Effect : Any>(
    val effect: Effect? = null,
    val state: State? = null
)

