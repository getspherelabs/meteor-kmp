package io.spherelabs.meteorviewmodel.concurrency

public actual typealias Lock = java.util.concurrent.locks.ReentrantLock

@Suppress("NOTHING_TO_INLINE")
public actual inline fun Lock.close() {}
