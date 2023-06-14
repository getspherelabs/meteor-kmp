import io.spherelabs.meteorviewmodel.flow.asCommonFlow
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class NonNullCommonStateFlowTest {

    @Test
    fun testStateFlow() = runTest {
        val stateFlow = MutableStateFlow(1)
        val flowWrapper = stateFlow.asCommonFlow()

        val values = mutableListOf<Int>()
        val job = launch(context = UnconfinedTestDispatcher(testScheduler)) {
            flowWrapper.toList(values)
        }

        stateFlow.value = 2
        stateFlow.value = 3
        stateFlow.value = 4

        job.cancelAndJoin()
        assertEquals(expected = listOf(1, 2, 3, 4), actual = values)
    }
}
