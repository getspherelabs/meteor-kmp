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
        var sentEffect: String? = null

        val to = To<String, String>(
            send = { sentEffect = it },
            state = initialState
        )

        assertEquals(initialState, to.state)

        to.send(effect)

        assertEquals(effect, sentEffect)
    }
}
