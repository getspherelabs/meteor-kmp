package io.spherelabs.meteorviewmodel.concurrency

internal actual class AtomicBoolean actual constructor(value: Boolean) {
    private val atomic = java.util.concurrent.atomic.AtomicBoolean(value)

    actual var value: Boolean
        get() = atomic.get()
        set(value) = atomic.set(value)

    actual fun compareAndSet(expected: Boolean, new: Boolean): Boolean {
        return atomic.compareAndSet(expected, new)
    }
}
