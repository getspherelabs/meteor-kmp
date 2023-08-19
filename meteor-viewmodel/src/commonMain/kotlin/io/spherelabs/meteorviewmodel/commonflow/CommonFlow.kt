package io.spherelabs.meteorviewmodel.commonflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

/**
 * [CommonFlow] provides common functionality for binding and observing a Flow of values. It implements the [Flow] interface
 * and delegates the actual flow.
 */
public abstract class CommonFlow<out T>(private val flow: Flow<T>) : Flow<T> by flow {

    /**
     * Binds the flow to the given coroutine scope and
     * invokes the provided callbacks for non-null values, failures, and completion.
     * @return A CommonJob representing the binding of the flow to the given scope.
     */
    public open fun bind(
        scope: CoroutineScope,
        values: (T) -> Unit,
        failure: ((failure: Throwable) -> Unit)? = null,
        completion: (() -> Unit)? = null
    ): CommonJob {
        return flow.bind(scope, values, failure, completion)
    }

    public open fun bind(
        scope: CoroutineScope,
        values: (T) -> Unit
    ): CommonJob {
        return flow.bind(scope, values, null, null)
    }

    public open fun bind(
        scope: CoroutineScope,
        values: (T) -> Unit,
        failure: ((failure: Throwable) -> Unit)
    ): CommonJob {
        return flow.bind(scope, values, failure, null)
    }

    public open fun bind(
        scope: CoroutineScope,
        values: (T) -> Unit,
        completion: (() -> Unit)
    ): CommonJob {
        return flow.bind(scope, values, null, completion)
    }
}

/**
 * Binds the flow to the given coroutine scope and
 * invokes the provided callbacks for non-null values, failures, and completion.
 * @return A CommonJob representing the binding of the flow to the given scope.
 */
internal fun <T> Flow<T>.bind(
    scope: CoroutineScope,
    values: (T) -> Unit,
    failure: ((failure: Throwable) -> Unit)?,
    completion: (() -> Unit)?
): CommonJob {
    val currentJob = this.onEach(values)
        .run {
            if (completion !== null) {
                onCompletion { failure ->
                    if (failure === null) completion() else throw failure
                }
            } else {
                this
            }
        }
        .run {
            if (failure !== null) {
                catch { message -> failure(message) }
            } else {
                this
            }
        }
        .launchIn(scope)

    return CoroutineCommonJob(currentJob)
}
