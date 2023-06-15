package flow

import app.cash.turbine.test
import io.spherelabs.meteorviewmodel.commonflow.asCommonFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class NonNullCommonStateFlowTest {
    @Test
    fun `should invoke values callback with non-null value`() = runTest {
        val internalState = MutableStateFlow(1)
        val state = internalState.asCommonFlow()

        state.test {
            assertEquals(1, awaitItem())
        }
        internalState.value = 2
        state.test {
            assertEquals(2, awaitItem())
        }
    }
}
