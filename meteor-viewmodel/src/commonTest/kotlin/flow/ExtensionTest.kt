package flow

import io.spherelabs.meteorviewmodel.commonflow.asCommonFlow
import io.spherelabs.meteorviewmodel.commonflow.asCommonStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertSame

class ExtensionTest {


    @Test
    fun testWrapper() = runTest {
        flowOf(1,2,3).asCommonFlow().let {
            assertSame(it, it.asCommonFlow())
        }
        MutableStateFlow(1).asCommonStateFlow().let {
            assertSame(it, it.asCommonStateFlow())
        }
    }
}
