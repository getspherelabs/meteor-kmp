package fake

sealed interface FakeComplexWish {
    object Increase : FakeComplexWish
    object Decrease : FakeComplexWish
    object Spike : FakeComplexWish
}
