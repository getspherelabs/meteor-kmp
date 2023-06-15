package fake

import io.spherelabs.meteor.middleware.Middleware

object FakeCountMiddleware : Middleware<FakeCountWish> {

    override suspend fun process(wish: FakeCountWish, next: suspend (FakeCountWish) -> Unit) {
        when (wish) {
            FakeCountWish.Decrease -> {
            }

            FakeCountWish.Increase -> {
            }

            else -> {
            }
        }
    }
}
