package io.spherelabs.countercompose.presentation

import io.spherelabs.meteor.middleware.Middleware

object CountMiddleware : Middleware<CountWish> {

    override suspend fun process(wish: CountWish, next: suspend (CountWish) -> Unit) {
        when (wish) {
            CountWish.Reset -> {
                next(CountWish.Toast("The values is removed"))
            }
            else -> {}
        }
    }
}
