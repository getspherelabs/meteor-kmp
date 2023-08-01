package io.spherelabs.meteorviewmodel.concurrency

internal actual inline fun <R> synchronized(lock: Lock, block: () -> R): R {
    return lock.withLock(block)
}
