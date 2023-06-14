package io.spherelabs.meteorviewmodel.flow

import io.spherelabs.meteorviewmodel.core.Closeable
import kotlinx.coroutines.Job

interface Joinable {
    public suspend fun join()
}

public interface Cancellable : Joinable, Closeable

class DefaultCancellable(private val job: Job) : Cancellable {
    override fun close() {
        job.cancel()
    }

    override suspend fun join() {
        job.join()
    }
}
