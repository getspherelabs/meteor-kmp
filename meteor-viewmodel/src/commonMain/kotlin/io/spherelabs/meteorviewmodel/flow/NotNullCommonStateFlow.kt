package io.spherelabs.meteorviewmodel.flow

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow

class NotNullCommonStateFlow<out T : Any> constructor(
    private val flow: StateFlow<T>
) : NonNullCommonFlow<T>(flow), StateFlow<T> by flow {
    override suspend fun collect(collector: FlowCollector<T>): Nothing = flow.collect(collector)
}

public fun <T : Any> StateFlow<T>.wrap(): NotNullCommonStateFlow<T> {
    return this as? NotNullCommonStateFlow<T> ?: NotNullCommonStateFlow(this)
}
