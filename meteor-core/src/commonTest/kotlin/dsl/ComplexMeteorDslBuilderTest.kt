package dsl

import fake.FakeComplexEffect
import fake.FakeComplexState
import fake.FakeComplexWish
import io.spherelabs.meteor.dsl.meteor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds

class ComplexMeteorDslBuilderTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()
    private val scope = TestScope(SupervisorJob())

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `check complex logic meteor dsl builder`() = runTest(
        timeout = 60.seconds
    ) {
        val meteor = meteor<FakeComplexState, FakeComplexWish, FakeComplexEffect> {
            config {
                initialState = FakeComplexState()
                name = "Fake Complex Meteor DSL"
            }
            reducer {
                on<FakeComplexWish.Increase> {
                    transition(copy(count = count + 1))
                }
                on<FakeComplexWish.Decrease> {
                    transition(copy(count = count - 1))
                }
                on<FakeComplexWish.Spike> {
                    transition(copy(count = count + 10))
                }
            }

            middleware {

            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun teardown() {
        Dispatchers.resetMain()
    }
}
