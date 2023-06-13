package io.spherelabs.meteorviewmodel.flow

import io.spherelabs.meteorviewmodel.core.Closeable
import kotlinx.coroutines.Job

interface Joinable {
    public suspend fun join()
}

public interface Cancelable : Joinable, Closeable

class DefaultCancelable(private val job: Job) : Cancelable {
    override fun close() {
        job.cancel()
    }

    override suspend fun join() {
        job.join()
    }
}
