package store

import fake.FakeEffect
import fake.FakeReducer
import fake.FakeState
import fake.FakeWish
import fake.fakeMiddleware
import io.spherelabs.meteor.configs.MeteorConfigs
import io.spherelabs.meteor.store.Store
import io.spherelabs.meteortest.store.createTestStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestDispatcher
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

class StoreTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val mainDispatcher: TestDispatcher = UnconfinedTestDispatcher()
    private val mainScope = TestScope(mainDispatcher)

    private lateinit var store: Store<FakeState, FakeWish, FakeEffect>

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        Dispatchers.setMain(mainDispatcher)
        store = mainScope.createTestStore(
            configs = MeteorConfigs.build {
                initialState = FakeState()
                storeName = "Test Meteor Store"
                reducer = FakeReducer
                middlewares = listOf(fakeMiddleware())
            }
        )
    }

    @Test
    fun `check when wish is called with increment then the state should be updated accordingly`() = runTest {
        store.wish(FakeWish.Increment)

        var count = 0

        assertEquals(1, store.currentState.count)
    }

    @Test
    fun `check  when wish is called decrement then the state should be update accordingly`() = runTest(
        timeout = 60.seconds
    ) {
        store.wish(FakeWish.Increment)
        store.wish(FakeWish.Increment)
        store.wish(FakeWish.Increment)

        store.wish(FakeWish.Decrement)

        assertEquals(2, store.currentState.count)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun teardown() {
        Dispatchers.resetMain()
        mainScope.cancel()
    }
}
