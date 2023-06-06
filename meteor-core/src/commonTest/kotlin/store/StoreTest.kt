package store

import fake.FakeEffect
import fake.FakeMiddleware
import fake.FakeReducer
import fake.FakeState
import fake.FakeWish
import io.spherelabs.meteor.configs.MeteorConfigs
import io.spherelabs.meteor.store.Store
import io.spherelabs.meteor.store.createMeteor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StoreTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testCoroutineDispatcher: TestDispatcher = UnconfinedTestDispatcher()
    private val mainScope = TestScope(testCoroutineDispatcher + Job())

    private lateinit var store: Store<FakeState, FakeWish, FakeEffect>

    @BeforeTest
    fun setup() {
        store = createMeteor(
            configs = MeteorConfigs.build {
                initialState = FakeState()
                storeName = "Test Meteor Store"
                reducer = FakeReducer
                middleware = FakeMiddleware
            },
            mainScope = mainScope
        )
    }

    @Test
    fun `check when wish is called with increment then the state should be updated accordingly`() = runTest {
        store.wish(FakeWish.Increment)

        var count = 0

        mainScope.launch {
            store.state.onEach {
                count = it.count
            }.launchIn(mainScope)
        }
        assertEquals(1, count)
    }

    @Test
    fun `check  when wish is called decrement then the state should be update accordingly`() = runTest {
        store.wish(FakeWish.Increment)
        store.wish(FakeWish.Increment)
        store.wish(FakeWish.Increment)

        store.wish(FakeWish.Decrement)

        assertEquals(2, store.currentState.count)
    }

    @AfterTest
    fun teardown() {
        mainScope.cancel()
    }

}
