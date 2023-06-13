package io.spherelabs.meteorviewmodel.core

import kotlinx.coroutines.CoroutineScope

public expect abstract class ViewModel {

    public constructor()

    public constructor(vararg closeables: Closeable)

    public val viewModelScope: CoroutineScope

    protected open fun onCleared()

    public fun addCloseable(closeables: Closeable)
}
