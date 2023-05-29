package reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
    fun `test reducer class works properly in increment`() = runBlocking {
        val internalState = MutableStateFlow(FakeState())
        val state = internalState.asStateFlow()

        internalState.value = FakeReducer.reduce(FakeState(), FakeWish.Increment)


        scope.launch {
            state.collect { state ->
                println("State: $state")
            }
        }
        assertEquals(1, state.value.count)
    }

    @Test
    fun `test reducer class works properly in decrement`() = runBlocking {
        val internalState = MutableStateFlow(FakeState(count = 5))
        val state = internalState.asStateFlow()

        internalState.value = FakeReducer.reduce(internalState.value, FakeWish.Decrement)

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
