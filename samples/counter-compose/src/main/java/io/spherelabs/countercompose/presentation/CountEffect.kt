package io.spherelabs.countercompose.presentation

sealed interface CountEffect {
    data class Failure(val message: String) : CountEffect
}
