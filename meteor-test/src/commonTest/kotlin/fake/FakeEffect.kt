package fake

sealed interface FakeEffect {
    data class Toast(val message: String) : FakeEffect

    sealed interface Route : FakeEffect {
        object Home : Route
    }
}
