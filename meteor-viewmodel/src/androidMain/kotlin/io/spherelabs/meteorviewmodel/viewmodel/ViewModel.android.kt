package io.spherelabs.meteorviewmodel.viewmodel

import io.spherelabs.meteorviewmodel.Closeable
import kotlinx.coroutines.CoroutineScope
import androidx.lifecycle.viewModelScope as vmScope

public actual abstract class ViewModel: androidx.lifecycle.ViewModel {
    public actual constructor(): super()

    public actual constructor(vararg closeables: Closeable): super(*closeables)

    public actual val viewModelScope: CoroutineScope = vmScope

    protected actual override fun onCleared(): Unit = super.onCleared()

    actual override fun addCloseable(closeables: Closeable) {
        super.addCloseable(closeables)
    }
}
