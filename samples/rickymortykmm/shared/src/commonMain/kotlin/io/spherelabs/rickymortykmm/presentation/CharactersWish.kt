package io.spherelabs.rickymortykmm.presentation

import io.spherelabs.rickymortykmm.remote.dto.CharacterDto

sealed interface CharactersWish {
    object CharacterStarted : CharactersWish
    data class GetCharacters(val characters: List<CharacterDto>) : CharactersWish
    data class RequestFailed(val message: String) : CharactersWish
    data class GetDetail(val id: Int) : CharactersWish
}
