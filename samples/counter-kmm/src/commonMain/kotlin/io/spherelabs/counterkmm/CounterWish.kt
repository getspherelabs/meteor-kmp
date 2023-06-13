package io.spherelabs.counterkmm

sealed interface CounterWish {
    object Increase : CounterWish
    object Decrease : CounterWish
    object Reset : CounterWish
    object Info: CounterWish
}
