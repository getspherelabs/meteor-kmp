package io.spherelabs.meteorviewmodel.core

public actual abstract class ViewModel: androidx.lifecycle.ViewModel {
    public actual constructor(): super()

    public actual constructor(vararg closeables: Closeable): super(*closeables)

    protected actual override fun onCleared(): Unit = super.onCleared()

    actual override fun addCloseable(closeables: Closeable) {
        super.addCloseable(closeables)
    }
}
