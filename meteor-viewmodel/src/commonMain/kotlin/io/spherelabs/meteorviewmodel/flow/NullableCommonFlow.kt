package io.spherelabs.meteorviewmodel.flow

import kotlinx.coroutines.flow.Flow
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

public open class NullableCommonFlow<out T : Any?> @OptIn(ExperimentalObjCName::class) constructor(
    @ObjCName("_")
    flow: Flow<T>
) : CommonFlow<T>(flow)

public fun <T> Flow<T>.asCommonFlow(): NullableCommonFlow<T> {
    return this as? NullableCommonFlow<T> ?: NullableCommonFlow(this)
}
