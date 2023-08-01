package io.spherelabs.meteorviewmodel.concurrency

import kotlin.concurrent.withLock

internal actual inline fun <R> synchronized(lock: Lock, block: () -> R): R {
    return lock.withLock(block)
}
