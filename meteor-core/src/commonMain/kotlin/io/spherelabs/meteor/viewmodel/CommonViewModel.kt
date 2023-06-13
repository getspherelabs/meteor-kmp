package io.spherelabs.meteor.viewmodel

import io.spherelabs.meteor.store.Store
import io.spherelabs.meteorviewmodel.core.ViewModel
import io.spherelabs.meteorviewmodel.flow.NonNullCommonFlow
import io.spherelabs.meteorviewmodel.flow.NotNullCommonStateFlow
import io.spherelabs.meteorviewmodel.flow.wrap
import kotlinx.coroutines.launch

abstract class CommonViewModel<State : Any, Wish : Any, Effect : Any> : ViewModel() {

    abstract val host: Store<State, Wish, Effect>

    val effect: NonNullCommonFlow<Effect> by lazy {
        host.effect.wrap()
    }

    val state: NotNullCommonStateFlow<State> by lazy {
        host.state.wrap()
    }

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

