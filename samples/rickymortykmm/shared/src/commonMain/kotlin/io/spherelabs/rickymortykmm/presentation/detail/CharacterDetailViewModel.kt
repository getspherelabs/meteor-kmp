package io.spherelabs.rickymortykmm.presentation.detail

import io.spherelabs.meteor.configs.MeteorConfigs
import io.spherelabs.meteor.store.Store
import io.spherelabs.meteor.viewmodel.CommonViewModel
import io.spherelabs.meteor.viewmodel.createMeteor
import io.spherelabs.meteorviewmodel.commonflow.NonNullCommonFlow
import io.spherelabs.meteorviewmodel.commonflow.NonNullCommonStateFlow
import io.spherelabs.meteorviewmodel.commonflow.asCommonFlow
import io.spherelabs.meteorviewmodel.commonflow.asCommonStateFlow

class CharacterDetailViewModel : CommonViewModel<CharacterDetailState, CharacterDetailWish, CharacterDetailEffect>() {

    override val store: Store<CharacterDetailState, CharacterDetailWish, CharacterDetailEffect> = createMeteor(
        configs = MeteorConfigs.build {
            initialState = CharacterDetailState.Empty
            storeName = "character_detail_store"
            reducer = CharacterDetailReducer
            middleware = CharacterDetailMiddleware
        }
    )

    val state: NonNullCommonStateFlow<CharacterDetailState> = store.state.asCommonStateFlow()
    val effect: NonNullCommonFlow<CharacterDetailEffect> = store.effect.asCommonFlow()
}
