package io.spherelabs.meteorviewmodel.concurrency

public expect class Lock {
    public fun lock()
    public fun unlock()
    public fun tryLock(): Boolean
}

public expect inline fun Lock.close()

public inline fun <T> Lock.withLock(block: () -> T): T {
    lock()
    try {
        return block()
    } finally {
        unlock()
    }
}
