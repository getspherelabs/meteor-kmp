import fake.FakeSuspendableUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SuspendableUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: FakeSuspendableUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun before() {
        Dispatchers.setMain(dispatcher)
        useCase = FakeSuspendableUseCase(dispatcher)
    }

    @Test
    fun shouldUseCaseWorksProperly() = runTest {
        var expected = ""

        val result = useCase(Unit)

        result.onSuccess {
            expected = it
        }

        assertEquals(expected, "10")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun teardown() {
        Dispatchers.resetMain()
    }
}
