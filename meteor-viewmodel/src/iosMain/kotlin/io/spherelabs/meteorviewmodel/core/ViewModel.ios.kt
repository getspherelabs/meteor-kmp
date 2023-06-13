package io.spherelabs.meteorviewmodel.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

public actual abstract class ViewModel : Any {
    private val closeables: MutableSet<Closeable>

    public actual constructor() : super() {
        this.closeables = linkedSetOf()
    }

    public actual constructor(vararg closeables: Closeable) : super() {
        this.closeables = LinkedHashSet(closeables.asList())
    }

    public actual val viewModelScope: CoroutineScope = CoroutineScope(SupervisorJob())

    protected actual open fun onCleared(): Unit = Unit

    public actual fun addCloseable(closeables: Closeable): Unit {
        this.closeables += closeables
    }
}
