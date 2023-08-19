package io.spherelabs.meteorviewmodel.commonflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * A [NonNullCommonFlow] is specialized type of [CommonFlow] that guarantees non-null values emitted by the underlying flow.
 * It extends the [CommonFlow] and provides additional safety by filtering out null values.
 * [ObjCName] instructs the Kotlin compiler to use a custom Objective-C and/or Swift name.
 */
@OptIn(ExperimentalObjCName::class)
public open class NonNullCommonFlow<out T : Any> constructor(
    @ObjCName("_")
    flow: Flow<T>
) : CommonFlow<T>(flow) {

    /**
     * Binds the flow to the given coroutine scope and
     * invokes the provided callbacks for non-null values, failures, and completion.
     * @return A CommonJob representing the binding of the flow to the given scope.
     */
    final override fun bind(
        scope: CoroutineScope,
        values: (T) -> Unit,
        failure: ((failure: Throwable) -> Unit)?,
        completion: (() -> Unit)?
    ): CommonJob {
        return super.bind(scope, values, failure, completion)
    }

    final override fun bind(
        scope: CoroutineScope,
        values: (T) -> Unit
    ): CommonJob {
        return super.bind(scope, values)
    }

    final override fun bind(
        scope: CoroutineScope,
        values: (T) -> Unit,
        failure: ((failure: Throwable) -> Unit)
    ): CommonJob {
        return super.bind(scope, values, failure = failure)
    }

    final override fun bind(
        scope: CoroutineScope,
        values: (T) -> Unit,
        completion: (() -> Unit)
    ): CommonJob {
        return super.bind(scope, values, completion = completion)
    }
}

/**
 * Represents this flow as a non-null common flow.
 */
public fun <T : Any> Flow<T>.asCommonFlow(): NonNullCommonFlow<T> =
    this as? NonNullCommonFlow<T> ?: NonNullCommonFlow(this)
