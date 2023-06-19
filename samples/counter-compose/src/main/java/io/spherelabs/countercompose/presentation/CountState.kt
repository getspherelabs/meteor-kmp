package io.spherelabs.countercompose.presentation

data class CountState(
    val count: Int = 0
) {
    companion object {
        val Empty = CountState()
    }
}
