package dsl

import fake.FakeEffect
import fake.FakeState
import fake.FakeWish
import io.spherelabs.meteor.dsl.meteor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.time.Duration.Companion.seconds

class MeteorDslBuilderTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `check meteor dsl builder works correctly`() = runTest(
        timeout = 60.seconds
    ) {

        var fakeEffect: FakeEffect? = null

        val meteor = meteor<FakeState, FakeWish, FakeEffect> {
            config {
                initialState = FakeState()
            }

            reducer {
                on<FakeWish.Decrement> {
                    transition(copy(count = count - 1))
                }
                on<FakeWish.Increment> {
                    transition(copy(count = count + 1))
                }
            }

            middleware {
                on { to, wish, next ->
                    when (wish) {
                        is FakeWish.Loading -> {
                            to.send(FakeEffect.Toast("1"))
                        }
                        is FakeWish.Increment -> {
                            next.invoke(FakeWish.Loading)
                        }
                        else -> {}
                    }
                }
            }

        }

        assertNotNull(meteor)

        testScope.launch {
            meteor.wish(FakeWish.Increment)
            meteor.wish(FakeWish.Increment)


            meteor.effect.onEach {
                 fakeEffect = it

            }.launchIn(testScope)

        }

        assertEquals(2, meteor.currentState.count)
        assertEquals(FakeEffect.Toast("1"),fakeEffect)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
