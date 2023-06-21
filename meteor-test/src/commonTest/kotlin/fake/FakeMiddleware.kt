package fake

import io.spherelabs.meteor.middleware.Middleware

object FakeMiddleware : Middleware<FakeState, FakeWish> {

    override suspend fun process(
        state: FakeState,
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
