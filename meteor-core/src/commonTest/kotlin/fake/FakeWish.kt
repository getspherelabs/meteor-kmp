package fake

sealed interface FakeWish {
    object Increment: FakeWish
    object Decrement: FakeWish
}
