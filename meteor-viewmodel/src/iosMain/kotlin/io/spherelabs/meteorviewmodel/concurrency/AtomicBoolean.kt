package io.spherelabs.meteorviewmodel.concurrency

import io.spherelabs.meteorviewmodel.core.toInt
import kotlin.native.concurrent.AtomicInt

internal actual class AtomicBoolean actual constructor(value: Boolean) {
    private val atomic = AtomicInt(value.toInt)

    actual var value: Boolean
        get() = atomic.value != 0
        set(value) {
            atomic.value = value.toInt
        }

    actual fun compareAndSet(expected: Boolean, new: Boolean): Boolean {
        return atomic.compareAndSet(expected.toInt, new.toInt)
    }
}
