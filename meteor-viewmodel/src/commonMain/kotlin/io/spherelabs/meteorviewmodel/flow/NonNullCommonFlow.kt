package io.spherelabs.meteorviewmodel.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@Suppress("RedundantOverride")
open class NonNullCommonFlow<out T : Any> @OptIn(ExperimentalObjCName::class) constructor(
    @ObjCName("_")
    flow: Flow<T>
) : CommonFlow<T>(flow) {

    override fun watchFlow(
        scope: CoroutineScope,
        values: (T) -> Unit,
        failure: (failure: Throwable) -> Unit,
        completion: () -> Unit
    ): Cancelable {
        return super.watchFlow(scope, values, failure, completion)
    }

    override fun watchFlow(scope: CoroutineScope, values: (T) -> Unit): Cancelable {
        return super.watchFlow(scope, values)
    }

    override fun watchFlow(scope: CoroutineScope, values: (T) -> Unit, failure: (failure: Throwable) -> Unit): Cancelable {
        return super.watchFlow(scope, values, failure)
    }

    override fun watchFlow(scope: CoroutineScope, values: (T) -> Unit, completion: () -> Unit): Cancelable {
        return super.watchFlow(scope, values, completion)
    }
}

public fun <T : Any> Flow<T>.wrap(): NonNullCommonFlow<T> =
    this as? NonNullCommonFlow<T> ?: NonNullCommonFlow(this)
