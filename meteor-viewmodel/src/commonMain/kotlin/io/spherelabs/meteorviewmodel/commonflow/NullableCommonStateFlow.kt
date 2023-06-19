package io.spherelabs.meteorviewmodel.commonflow

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * [NullableCommonStateFlow] allows nullable values to be emitted by underlying [StateFlow].
 *
 * [ObjCName] instructs the Kotlin compiler to use a custom Objective-C and/or Swift name.
 */

@OptIn(ExperimentalObjCName::class)
public class NullableCommonStateFlow<T> constructor(
    @ObjCName("_")
    private val flow: StateFlow<T>
) : NullableCommonFlow<T>(flow), StateFlow<T> by flow {

    override suspend fun collect(collector: FlowCollector<T>): Nothing = flow.collect(collector)
}

/**
 * Represents this a read-only state flow as nullable common state flow.
 */

public fun <T> StateFlow<T>.asCommonStateFlow(): NullableCommonStateFlow<T> {
    return this as? NullableCommonStateFlow<T> ?: NullableCommonStateFlow(this)
}
