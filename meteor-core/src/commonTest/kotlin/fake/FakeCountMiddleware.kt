package fake

import io.spherelabs.meteor.middleware.CoroutineMiddleware
import io.spherelabs.meteor.middleware.Middleware
import io.spherelabs.meteorlogger.log
import kotlinx.coroutines.CoroutineScope

fun countMiddleware() = object : CoroutineMiddleware<FakeCountState, FakeCountWish> {
    override suspend fun CoroutineScope.handle(state: FakeCountState, wish: FakeCountWish, next: suspend (FakeCountWish) -> Unit) {
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
