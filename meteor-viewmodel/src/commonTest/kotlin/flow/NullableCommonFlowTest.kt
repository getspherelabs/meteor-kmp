package flow

import io.spherelabs.meteorviewmodel.commonflow.NullableCommonFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals

class NullableCommonFlowTest {

    @Test
    fun `should invoke values callback with nullable value`() = runTest {
        val flowResult = flowOf(1, 2, 3, 4, 5, null)
        val commonFlow = NullableCommonFlow(flowResult)

        val numbers = mutableListOf<Int?>()
        var failure = null as Throwable?
        var completed = false

        commonFlow.bind(
            scope = this,
            values = {
                numbers += it
            },
            failure = {
                failure = it
            },
            completion = {
                completed = true
            }
        ).join()

        assertContentEquals(expected = listOf(1, 2, 3, 4, 5, null), actual = numbers)
    }
}
