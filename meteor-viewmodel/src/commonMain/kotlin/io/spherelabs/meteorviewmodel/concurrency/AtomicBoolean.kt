package io.spherelabs.meteorviewmodel.concurrency

internal expect class AtomicBoolean(value: Boolean = false) {
    var value: Boolean
    fun compareAndSet(expected: Boolean, new: Boolean): Boolean
}
