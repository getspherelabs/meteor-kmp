package flow

import io.spherelabs.meteorviewmodel.commonflow.NonNullCommonFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertTrue

class NonNullCommonFlowTest {

    @Test
    fun `should invoke values callback with non-null value`() = runTest {
        val numbers = mutableListOf<Int>()
        var error = null as Throwable?
        var completed = false

        NonNullCommonFlow(flowOf(1, 2, 3, 4, 5))
            .bind(
                scope = this,
                values = {
                    numbers.add(it)
                },
                failure = { error = it }
            ).join()

        assertContentEquals(expected = listOf(1, 2, 3, 4, 5), actual = numbers)
    }

    @Test
    fun `should invoke completion callback when flow completes without emitting null values`() = runTest {
        val numbers = mutableListOf<Int>()
        var completed = false

        val state = NonNullCommonFlow(flowOf(1)).bind(
            scope = this,
            values = { number ->
                numbers.add(number)
            },
            completion = {
                completed = true
            }
        ).join()

        assertTrue(completed)
    }
}
