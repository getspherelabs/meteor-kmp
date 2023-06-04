package fake

sealed interface FakeComplexEffect {
    data class Toast(val message: String) : FakeComplexWish
}
