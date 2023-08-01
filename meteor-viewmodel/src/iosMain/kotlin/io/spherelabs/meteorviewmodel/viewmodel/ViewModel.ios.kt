package io.spherelabs.meteorviewmodel.viewmodel

import io.spherelabs.meteorviewmodel.Closeable
import io.spherelabs.meteorviewmodel.concurrency.AtomicBoolean
import io.spherelabs.meteorviewmodel.concurrency.Lock
import io.spherelabs.meteorviewmodel.concurrency.synchronized
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

public actual abstract class ViewModel : Any {
    private val lock: Lock = Lock()
    private val isCleared = AtomicBoolean(false)
    private val closeables: MutableSet<Closeable>

    public actual constructor() : super() {
        this.closeables = linkedSetOf()
    }

    public actual constructor(vararg closeables: Closeable) : super() {
        this.closeables = LinkedHashSet(closeables.asList())
    }

    private val coroutineScopeLazy = lazy {
        check(!isCleared.value) { "Cannot access viewModelScope on a cleared ViewModel" }
        CoroutineScope(SupervisorJob() + viewModelDispatcher())
    }

    public actual val viewModelScope: CoroutineScope by coroutineScopeLazy

    protected actual open fun onCleared(): Unit = Unit

    public actual fun addCloseable(closeables: Closeable): Unit = synchronized(lock) {
        check(!isCleared.value) { "Cannot addCloseable on a cleared ViewModel" }
        this.closeables += closeables
    }

    public fun clear(): Unit = synchronized(lock) {
        if (isCleared.compareAndSet(expected = false, new = true)) {
            if (coroutineScopeLazy.isInitialized()) {
                coroutineScopeLazy.value.cancel()
            }

            closeables.forEach { it.close() }
            closeables.clear()

            onCleared()
        }
    }
}

private inline fun viewModelDispatcher(): CoroutineDispatcher =
    runCatching { Dispatchers.Main.immediate }
        .getOrDefault(Dispatchers.Main)
