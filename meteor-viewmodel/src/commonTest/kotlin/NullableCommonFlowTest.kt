import io.spherelabs.meteorviewmodel.flow.NullableCommonFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals

class NullableCommonFlowTest {

    @Test
    fun checkNullableCommonFlowWorksProperly() = runTest {
        val numbers = mutableListOf<Int?>()
        var failure = null as Throwable?
        var completed = false

        NullableCommonFlow(flowOf(1, 2, 3, 4, 5, null)).watchFlow(
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

        assertContentEquals(expected = listOf(1, 2, 3, 4,5,null), actual = numbers)
    }
}
