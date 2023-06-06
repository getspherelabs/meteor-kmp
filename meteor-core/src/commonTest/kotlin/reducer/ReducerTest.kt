package reducer

import fake.FakeReducer
import fake.FakeState
import fake.FakeWish
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ReducerTest {

    private lateinit var scope: CoroutineScope

    @BeforeTest
    fun setup() {
        scope = CoroutineScope(Job())
    }

    @Test
    fun `test reducer class works properly in increment`() = runTest {
        val internalState = MutableStateFlow(FakeState())
        val state = internalState.asStateFlow()

        internalState.value = checkNotNull(FakeReducer.reduce(FakeState(), FakeWish.Increment).state)


        scope.launch {
            state.collect { state ->
                println("State: $state")
            }
        }
        assertEquals(1, state.value.count)
    }

    @Test
    fun `test reducer class works properly in decrement`() = runTest {
        val internalState = MutableStateFlow(FakeState(count = 5))
        val state = internalState.asStateFlow()

        internalState.value = FakeReducer.reduce(internalState.value, FakeWish.Decrement).state ?: FakeState()

        scope.launch {
            state.collect { state ->
                println("State: $state")
            }
        }

        assertEquals(4, state.value.count)
    }

    @AfterTest
    fun teardown() {
        scope.cancel()
    }
}
