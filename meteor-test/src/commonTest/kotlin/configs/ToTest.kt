package configs

import io.spherelabs.meteor.configs.Change
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ToTest {

    @Test
    fun `test To class`() = runTest {
        val initialState = "Initial State"
        val effect = "Effect"

        val change = Change(
            effect = effect,
            state = initialState
        )

        assertEquals(initialState, change.state)
        assertEquals(effect, change.effect)
    }
}
