package io.spherelabs.meteorviewmodel.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

open class NonNullCommonFlow<out T : Any> @OptIn(ExperimentalObjCName::class) constructor(
    @ObjCName("_")
    flow: Flow<T>
) : CommonFlow<T>(flow) {

     final override fun watchFlow(
        scope: CoroutineScope,
        values: (T) -> Unit,
        failure: ((failure: Throwable) -> Unit)?,
        completion: (() -> Unit)?
    ): Cancellable {
         println("The values are $values")
        return super.watchFlow(scope, values, failure, completion)
    }

}

public fun <T : Any> Flow<T>.asCommonFlow(): NonNullCommonFlow<T> =
    this as? NonNullCommonFlow<T> ?: NonNullCommonFlow(this)
