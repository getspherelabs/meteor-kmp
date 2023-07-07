package io.spherelabs.countercompose.presentation

import io.spherelabs.meteor.middleware.Middleware

fun countMiddleware() = object : Middleware<CountState, CountWish> {

    override suspend fun process(state: CountState, wish: CountWish, next: suspend (CountWish) -> Unit) {
        when (wish) {
            CountWish.Reset -> {
                next(CountWish.Toast("The values is removed"))
            }
            else -> {}
        }
    }
}
