package fake

sealed interface FakeCountWish {
    object Increase : FakeCountWish
    object Decrease : FakeCountWish
    object Reset : FakeCountWish
    object ZeroValue : FakeCountWish
}
