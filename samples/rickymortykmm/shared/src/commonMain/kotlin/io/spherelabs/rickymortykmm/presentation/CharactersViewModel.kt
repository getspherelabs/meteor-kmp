package io.spherelabs.rickymortykmm.presentation

import io.spherelabs.meteor.configs.MeteorConfigs
import io.spherelabs.meteor.store.Store
import io.spherelabs.meteor.viewmodel.CommonViewModel
import io.spherelabs.meteor.viewmodel.createMeteor
import io.spherelabs.meteorviewmodel.commonflow.NonNullCommonFlow
import io.spherelabs.meteorviewmodel.commonflow.NonNullCommonStateFlow
import io.spherelabs.meteorviewmodel.commonflow.asCommonFlow
import io.spherelabs.meteorviewmodel.commonflow.asCommonStateFlow

class CharactersViewModel(
    private val charactersMiddleware: CharactersMiddleware
) : CommonViewModel<CharactersState, CharactersWish, CharactersEffect>() {

    override val store: Store<CharactersState, CharactersWish, CharactersEffect> = createMeteor(
        configs = MeteorConfigs.build {
            initialState = CharactersState()
            storeName = "Characters Store"
            reducer = CharactersReducer
            middleware = charactersMiddleware
        }
    )

    val state: NonNullCommonStateFlow<CharactersState> = store.state.asCommonStateFlow()
    val effect: NonNullCommonFlow<CharactersEffect> = store.effect.asCommonFlow()
}
