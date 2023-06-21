package io.spherelabs.rickymortykmm.presentation

import io.spherelabs.rickymortykmm.remote.dto.CharacterDto

data class CharactersState(
    val isLoading: Boolean = false,
    val characters: List<CharacterDto> = emptyList()
)
