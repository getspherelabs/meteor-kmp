package io.spherelabs.meteorviewmodel.concurrency

internal expect inline fun <R> synchronized(lock: Lock, block: () -> R): R
