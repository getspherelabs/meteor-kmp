import fake.FakeCommonUseCase
import fake.TestStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds

class CommonUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()
    private val scope = TestScope()

    private lateinit var testUseCase: FakeCommonUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun before() {
        Dispatchers.setMain(dispatcher)
        testUseCase = FakeCommonUseCase(dispatcher)
    }

    @Test
    fun shouldUseCaseWorksProperly() = runTest(
        timeout = 60.seconds
    ) {
        var actual = ""

        testUseCase(TestStatus.SUCCESS).collectLatest { result ->
            result.onSuccess {
                assertEquals("10", it)
            }
        }
    }

    @Test
    fun shouldUseCaseWorksProperlyWhenFailure() = runTest(
        timeout = 60.seconds
    ) {
        val npe = NullPointerException().message

        testUseCase(TestStatus.FAILURE).collectLatest { result ->
            result.onFailure {
                assertEquals(npe, it.message)
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@AfterTest
fun teardown() {
    Dispatchers.resetMain()
}
