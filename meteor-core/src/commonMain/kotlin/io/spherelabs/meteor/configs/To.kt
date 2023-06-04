package io.spherelabs.meteor.configs

data class To<State : Any, Effect : Any>(
    val send: suspend (Effect) -> Unit,
    val state: State? = null
)

