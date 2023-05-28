package configs

import io.spherelabs.meteor.configs.To
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class ToTest {

    @Test
    fun `test To class`() = runBlocking {
        // Given
        val initialState = "Initial State"
        val effect = "Effect"
        var sentEffect: String? = null

        // When
        val to = To<String, String>(
            send = { sentEffect = it },
            state = initialState
        )

        // Then
        assertEquals(initialState, to.state)

        // Verify that the effect is sent correctly
        to.send(effect)

        assertEquals(effect, sentEffect)
    }
}
