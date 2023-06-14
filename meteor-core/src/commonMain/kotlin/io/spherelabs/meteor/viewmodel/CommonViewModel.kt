package io.spherelabs.meteor.viewmodel

import io.spherelabs.meteor.store.Store
import io.spherelabs.meteorviewmodel.core.Closeable
import io.spherelabs.meteorviewmodel.core.ViewModel
import io.spherelabs.meteorviewmodel.flow.NonNullCommonFlow
import io.spherelabs.meteorviewmodel.flow.NotNullCommonStateFlow
import io.spherelabs.meteorviewmodel.flow.asCommonFlow

import kotlinx.coroutines.launch

abstract class CommonViewModel<State : Any, Wish : Any, Effect : Any> : ViewModel {

    constructor() : super()

    constructor(closeables: List<Closeable>) : super(*closeables.toTypedArray())

    abstract val host: Store<State, Wish, Effect>

    var count = 0
    fun wish(wish: Wish) {
        viewModelScope.launch {
            count++
            println("Wish is $count")
            println("Viewmodelscope is $viewModelScope")
            host.wish(wish)
        }
    }

    override fun onCleared() {
        super.onCleared()
        host.cancel()
    }
}

