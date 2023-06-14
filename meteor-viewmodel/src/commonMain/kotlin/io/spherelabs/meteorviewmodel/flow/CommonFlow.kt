package io.spherelabs.meteorviewmodel.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

public abstract class CommonFlow<out T>(private val flow: Flow<T>) : Flow<T> by flow {

    public open fun watchFlow(
        scope: CoroutineScope,
        values: (T) -> Unit,
        failure: ((failure: Throwable) -> Unit)? = null,
        completion: (() -> Unit)? = null
    ): Cancellable {
        return flow.watch(scope, values, failure, completion)
    }
}


internal fun <T> Flow<T>.watch(
    scope: CoroutineScope,
    values: (T) -> Unit,
    failure: ((failure: Throwable) -> Unit)?,
    completion: (() -> Unit)?
): Cancellable {
    val currentJob = this.onEach(values)
        .run {
            println("Values are $values")
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

    return DefaultCancellable(currentJob)
}
