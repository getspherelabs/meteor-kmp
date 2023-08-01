package io.spherelabs.meteorviewmodel.concurrency

import platform.Foundation.NSRecursiveLock

public actual typealias Lock = NSRecursiveLock

@Suppress("NOTHING_TO_INLINE")
public actual inline fun Lock.close() {}
