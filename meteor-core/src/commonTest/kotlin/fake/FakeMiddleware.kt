package fake

import io.spherelabs.meteor.Middleware
import io.spherelabs.meteor.configs.To

object FakeMiddleware : Middleware<FakeState, FakeWish, FakeEffect> {

    override suspend fun process(
        effect: To<FakeState, FakeEffect>,
        wish: FakeWish,
        next: suspend (FakeWish) -> Unit
    ) {
        when(wish)   {
            FakeWish.Decrement -> {
                effect.send(FakeEffect(message = "Decrement is not triggered"))
            }
            FakeWish.Increment -> {
                next(wish)
            }
        }
    }
}
