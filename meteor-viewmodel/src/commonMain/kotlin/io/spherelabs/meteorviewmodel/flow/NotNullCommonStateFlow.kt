package io.spherelabs.meteorviewmodel.flow

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

class NotNullCommonStateFlow<out T : Any> @OptIn(ExperimentalObjCName::class) constructor(
    @ObjCName("_")
    private val flow: StateFlow<T>
) : NonNullCommonFlow<T>(flow), StateFlow<T> by flow {
    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        println("Collector $collector")
        flow.collect(collector)
    }
}

public fun <T : Any> StateFlow<T>.asCommonFlow(): NotNullCommonStateFlow<T> {
    return this as? NotNullCommonStateFlow<T> ?: NotNullCommonStateFlow(this)
}
