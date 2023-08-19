package io.spherelabs.meteorviewmodel.concurrency

import platform.Foundation.NSRecursiveLock

public actual typealias Lock = NSRecursiveLock

public actual inline fun Lock.close() {}
