package io.spherelabs.rickymortykmm.presentation.detail

sealed interface CharacterDetailEffect {
    data class Failure(val msg: String) : CharacterDetailEffect
}
