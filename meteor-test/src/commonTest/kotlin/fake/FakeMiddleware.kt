package fake

import io.spherelabs.meteor.middleware.Middleware

object FakeMiddleware : Middleware<FakeWish> {

    override suspend fun process(
        wish: FakeWish,
        next: suspend (FakeWish) -> Unit
    ) {
        when (wish) {
            FakeWish.Decrement -> {
            }

            FakeWish.Increment -> {
            }

            else -> {
            }
        }
    }
}
