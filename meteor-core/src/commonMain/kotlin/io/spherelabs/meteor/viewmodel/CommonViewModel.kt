package io.spherelabs.meteor.viewmodel

import io.spherelabs.meteor.store.Store
import io.spherelabs.meteorviewmodel.Closeable
import io.spherelabs.meteorviewmodel.viewmodel.ViewModel
import kotlinx.coroutines.launch

abstract class CommonViewModel<State : Any, Wish : Any, Effect : Any> : ViewModel {

    constructor() : super()

    constructor(closeables: List<Closeable>) : super(*closeables.toTypedArray())

    abstract val store: Store<State, Wish, Effect>

    fun wish(wish: Wish) {
        viewModelScope.launch {
            store.wish(wish)
        }
    }

    override fun onCleared() {
        super.onCleared()
        store.cancel()
    }
}
