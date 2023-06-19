package io.spherelabs.meteorviewmodel.commonflow

import io.spherelabs.meteorviewmodel.Closeable
import kotlinx.coroutines.Job

/**
 * A [CommonJob] is common background job, a common job is cancellable with life-cycle.
 * When a coroutine is started, it returns a Job object that can be used to track and control the coroutine's execution.
 * The basic instance of [CommonJob] creates with [CoroutineCommonJob].
 * It also implements [Closeable] interface for managing and closing resources.
 */
public interface CommonJob : Closeable {
    /**
     * Suspends the invoking coroutine until this job is complete.
     */
    public suspend fun join()
}

/**
 * A [CoroutineCommonJob] is the implementation of [CommonJob].
 * It represents a common background job in coroutines.
 * It allows tracking and controlling the execution of a coroutine.
 */
public class CoroutineCommonJob(
    private val job: Job
) : CommonJob {
    override suspend fun join() {
        job.join()
    }

    override fun close() {
        job.cancel()
    }
}
