package fake

import io.spherelabs.meteor.middleware.Middleware

object FakeCountMiddleware : Middleware<FakeCountState, FakeCountWish> {

    override suspend fun process(state: FakeCountState, wish: FakeCountWish, next: suspend (FakeCountWish) -> Unit) {
        when (wish) {
            FakeCountWish.Decrease -> {
                if (state.count == 0) {
                    next(FakeCountWish.ZeroValue)
                }
            }

            FakeCountWish.Increase -> {
            }

            else -> {
            }
        }
    }
}
