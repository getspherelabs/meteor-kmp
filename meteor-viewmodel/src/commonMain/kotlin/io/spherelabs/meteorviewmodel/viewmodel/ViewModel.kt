package io.spherelabs.meteorviewmodel.viewmodel

import io.spherelabs.meteorviewmodel.Closeable
import kotlinx.coroutines.CoroutineScope

/**
 * A [ViewModel] provides a foundation for implementing view model in a multiplatform environment.
 * It encapsulates common functionality and lifecycle for common viewmodels.
 */
public expect abstract class ViewModel {

    public constructor()

    public constructor(vararg closeables: Closeable)

    public val viewModelScope: CoroutineScope

    protected open fun onCleared()

    public fun addCloseable(closeables: Closeable)
}
