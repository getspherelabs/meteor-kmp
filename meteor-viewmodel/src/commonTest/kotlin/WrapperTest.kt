import io.spherelabs.meteorviewmodel.flow.asCommonFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertSame

class WrapperTest {


    @Test
    fun testWrapper() = runTest {
        flowOf(1,2,3).asCommonFlow().let {
            assertSame(it, it.asCommonFlow())
        }
        MutableStateFlow(1).asCommonFlow().let {
            assertSame(it, it.asCommonFlow())
        }
    }
}
