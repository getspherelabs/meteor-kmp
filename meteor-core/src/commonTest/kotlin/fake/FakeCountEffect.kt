package fake

sealed interface FakeCountEffect {
    data class Failure(val message: String) : FakeCountEffect
}
