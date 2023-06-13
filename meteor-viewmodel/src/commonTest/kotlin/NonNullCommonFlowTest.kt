import io.spherelabs.meteorviewmodel.flow.NonNullCommonFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertTrue

class NonNullCommonFlowTest {

    @Test
    fun checkExecutesCommonFlowProperly() = runTest {
        val numbers = mutableListOf<Int>()
        var error = null as Throwable?
        var completed = false

        NonNullCommonFlow(flowOf(1, 2, 3, 4, 5))
            .watchFlow(
                scope = this,
                values = {
                    numbers.add(it)
                },
                failure = { error = it },
                completion = { completed = true }
            )
            .join()

        assertContentEquals(expected = listOf(1, 2, 3, 4, 5), actual = numbers)
        assertTrue(completed)
    }
}
