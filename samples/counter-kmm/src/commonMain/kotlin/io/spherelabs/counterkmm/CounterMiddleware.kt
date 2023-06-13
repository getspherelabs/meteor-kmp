package io.spherelabs.counterkmm

import io.spherelabs.meteor.middleware.Middleware

object CounterMiddleware: Middleware<CounterWish> {

    override suspend fun process(wish: CounterWish, next: suspend (CounterWish) -> Unit) {
        return when(wish) {
            CounterWish.Decrease -> {
                next(CounterWish.Info)
            }
            else -> {}
        }
    }
}
