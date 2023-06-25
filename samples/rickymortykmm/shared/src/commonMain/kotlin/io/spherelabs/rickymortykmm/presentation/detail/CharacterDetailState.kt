package io.spherelabs.rickymortykmm.presentation.detail

import io.spherelabs.rickymortykmm.remote.dto.CharacterDto

data class CharacterDetailState(
    val character: CharacterDto? = null,
    val isLoading: Boolean = false
) {
    companion object {
        val Empty = CharacterDetailState()
    }
}
