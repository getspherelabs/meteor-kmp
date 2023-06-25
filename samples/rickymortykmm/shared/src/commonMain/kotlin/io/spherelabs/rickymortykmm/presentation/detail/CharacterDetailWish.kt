package io.spherelabs.rickymortykmm.presentation.detail

import io.spherelabs.rickymortykmm.remote.dto.CharacterDto

sealed interface CharacterDetailWish {
    data class GetCharacterById(val id: Int) : CharacterDetailWish
    object GettingLoading : CharacterDetailWish
    data class GettingCompleted(val character: CharacterDto) : CharacterDetailWish
    data class Toast(val msg: String) : CharacterDetailWish
}
