package middleware

import fake.FakeEffect
import fake.FakeMiddleware
import fake.FakeState
import fake.FakeWish
import io.spherelabs.meteor.configs.To
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MiddlewareTest {

    @Test
    fun `check the middleware works correctly`() = runTest {
        var processedWish: FakeWish? = null
        var processedEffect: FakeEffect? = null

        val nextFunction: suspend (FakeWish) -> Unit = { wish -> processedWish = wish }
        val effectFunction: suspend (FakeEffect) -> Unit = { effect -> processedEffect = effect }

        FakeMiddleware.process(To(effectFunction, FakeState()), FakeWish.Decrement, nextFunction)

        assertEquals(FakeEffect("Decrement is not triggered"), processedEffect)
    }
}
