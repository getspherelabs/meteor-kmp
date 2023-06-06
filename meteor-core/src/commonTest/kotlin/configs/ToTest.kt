package configs

import io.spherelabs.meteor.configs.To
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ToTest {

    @Test
    fun `test To class`() = runTest {
        val initialState = "Initial State"
        val effect = "Effect"

        val to = To(
            effect = effect,
            state = initialState
        )

        assertEquals(initialState, to.state)
        assertEquals(effect, to.effect)
    }
}
