package reducer

import fake.FakeReducer
import fake.FakeState
import fake.FakeWish
import io.spherelabs.meteortest.reducer.runReducerTestWithState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ReducerTest {

    private lateinit var testDispatcher: CoroutineDispatcher
    private lateinit var testScope: TestScope

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        testDispatcher = UnconfinedTestDispatcher()
        testScope = TestScope(testDispatcher)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `test reducer class works properly in increment`() = runTest {
        val fakeState = FakeState()

        FakeReducer.runReducerTestWithState(
            fakeState,
            FakeWish.Increment,
            testScope,
        ) { state ->
            assertEquals(1, state.count)
        }
    }

    @Test
    fun `test reducer class works properly in decrement`() = runTest {
        val fakeState = FakeState(count = 5)
        FakeReducer.runReducerTestWithState(
            fakeState,
            FakeWish.Decrement,
            testScope
        ) { newState ->
            assertEquals(4, newState.count)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun teardown() {
        Dispatchers.resetMain()
        testScope.cancel()
    }
}
