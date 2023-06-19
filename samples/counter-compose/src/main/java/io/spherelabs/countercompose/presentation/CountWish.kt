package io.spherelabs.countercompose.presentation

sealed interface CountWish {
    object Increase : CountWish
    object Decrease : CountWish
    object Reset : CountWish
    data class Toast(val message: String) : CountWish
}
