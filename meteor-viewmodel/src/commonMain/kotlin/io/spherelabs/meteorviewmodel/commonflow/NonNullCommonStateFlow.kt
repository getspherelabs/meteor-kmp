package io.spherelabs.meteorviewmodel.commonflow

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * A [NonNullCommonStateFlow] is a type of [NonNullCommonFlow] which is delegated by a [StateFlow].
 * It guarantees non-nul values emitted by the underlying StateFlow.
 * [ObjCName] instructs the Kotlin compiler to use a custom Objective-C and/or Swift name/
 */
public class NonNullCommonStateFlow<out T : Any>
@OptIn(ExperimentalObjCName::class)
constructor(
    @ObjCName("_")
    private val flow: StateFlow<T>
) : NonNullCommonFlow<T>(flow), StateFlow<T> by flow {
    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        flow.collect(collector)
    }
}

/**
 * Represents this read-only state flow as a non-null common state flow.
 */
public fun <T : Any> StateFlow<T>.asCommonStateFlow(): NonNullCommonStateFlow<T> {
    return this as? NonNullCommonStateFlow<T> ?: NonNullCommonStateFlow(this)
}
