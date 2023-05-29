package reducer

sealed interface FakeWish {
    object Increment: FakeWish
    object Decrement: FakeWish
}
