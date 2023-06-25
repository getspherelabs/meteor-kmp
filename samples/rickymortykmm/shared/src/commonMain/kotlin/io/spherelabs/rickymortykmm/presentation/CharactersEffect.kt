package io.spherelabs.rickymortykmm.presentation

sealed interface CharactersEffect {
    data class Failure(val message: String) : CharactersEffect
    data class GetDetail(val id: Int) : CharactersEffect
}
