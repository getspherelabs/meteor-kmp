package io.spherelabs.meteor.viewmodel

import io.spherelabs.meteor.store.Store
import kotlinx.coroutines.launch

abstract class CommonViewModel<State : Any, Wish : Any, Effect : Any> : ViewModel() {

    abstract val host: Store<State, Wish, Effect>

    fun wish(wish: Wish) {
        viewModelScope.launch {
            host.wish(wish)
        }
    }

    override fun onCleared() {
        super.onCleared()
        host.cancel()
    }
}

