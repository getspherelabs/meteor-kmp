package middleware

import io.spherelabs.meteor.Middleware
import io.spherelabs.meteor.configs.To
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class MiddlewareTest {

    @Test
    fun `check the middleware works correctly`() = runBlocking {
        val middleware = object: Middleware<Int, String, FakeMiddlewareEffect> {
            override suspend fun process(
                effect: To<Int, FakeMiddlewareEffect>,
                wish: String,
                next: suspend (String) -> Unit
            ) {
                val newWish = "Processed $wish"

                next(newWish)

                effect.send(FakeMiddlewareEffect("Effect is triggered"))
            }
        }

        var processedWish: String? = null
        var processedEffect: FakeMiddlewareEffect? = null

        val nextFunction: suspend (String) -> Unit = { wish -> processedWish = wish }
        val effectFunction: suspend (FakeMiddlewareEffect) -> Unit = { effect -> processedEffect = effect }

        middleware.process(To(effectFunction, 0), "Middleware", nextFunction)

        assertEquals("Processed Middleware", processedWish)
        assertEquals(FakeMiddlewareEffect("Effect is triggered"), processedEffect)
    }
}
