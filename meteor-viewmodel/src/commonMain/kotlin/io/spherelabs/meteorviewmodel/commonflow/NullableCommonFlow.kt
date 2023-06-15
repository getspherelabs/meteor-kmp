package io.spherelabs.meteorviewmodel.commonflow

import kotlinx.coroutines.flow.Flow
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * [NullableCommonStateFlow] allows nullable values to be emitted by underlying [Flow].
 *
 * [ObjCName] instructs the Kotlin compiler to use a custom Objective-C and/or Swift name.
 */
public open class NullableCommonFlow<out T : Any?> constructor(
    @OptIn(ExperimentalObjCName::class)
    @ObjCName("_")
    flow: Flow<T>
) : CommonFlow<T>(flow)

public fun <T> Flow<T>.asCommonFlow(): NullableCommonFlow<T> {
    return this as? NullableCommonFlow<T> ?: NullableCommonFlow(this)
}
