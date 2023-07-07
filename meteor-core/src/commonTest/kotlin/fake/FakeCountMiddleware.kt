package fake

import io.spherelabs.meteor.middleware.Middleware
import io.spherelabs.meteorlogger.log

fun countMiddleware() = object : Middleware<FakeCountState, FakeCountWish> {
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

fun loggingMiddleware() = object : Middleware<FakeCountState, FakeCountWish> {
    override suspend fun process(state: FakeCountState, wish: FakeCountWish, next: suspend (FakeCountWish) -> Unit) {
        when (wish) {
            FakeCountWish.Decrease -> {
                log("Logging")
            }

            else -> {
                log("else")
            }
        }
    }
}
