package io.spherelabs.counterkmm

sealed interface CounterEffect {
    data class Failure(val message: String) : CounterEffect
}
