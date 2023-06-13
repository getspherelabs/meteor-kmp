package io.spherelabs.meteorviewmodel.core

public expect abstract class ViewModel {

    public constructor()

    public constructor(vararg closeables: Closeable)

    protected open fun onCleared()

    public fun addCloseable(closeables: Closeable)
}
