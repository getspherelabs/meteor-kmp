package fake

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds

class CommonViewModelTest {

    private lateinit var viewModel: FakeViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    private val mainDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        Dispatchers.setMain(mainDispatcher)
        viewModel = FakeViewModel()
    }

    @Test
    fun `test view model works properly`() = runTest(
        timeout = 60.seconds
    ) {
        viewModel.wish(FakeCountWish.Increase)
        viewModel.wish(FakeCountWish.Decrease)

        viewModel.state.test {
            assertEquals(
                FakeCountState(0),
                awaitItem()
            )
        }

        viewModel.effect.test {
            assertEquals(
                FakeCountEffect.Failure("The value is zero"),
                awaitItem()
            )
        }
    }

    @Test
    fun `check the increase and decrease actions works properly`() = runTest {
        viewModel.wish(FakeCountWish.Increase)
        viewModel.wish(FakeCountWish.Increase)

        assertEquals(2, viewModel.state.value.count)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun teardown() {
        Dispatchers.resetMain()
    }
}
