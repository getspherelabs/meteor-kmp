package configs

import fake.FakeEffect
import fake.FakeReducer
import fake.FakeState
import fake.FakeWish
import fake.fakeMiddleware
import io.spherelabs.meteor.configs.MeteorConfigs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MeteorConfigsTest {

    private lateinit var configs: MeteorConfigs<FakeState, FakeWish, FakeEffect>

    @BeforeTest
    fun setup() {
        configs = MeteorConfigs.build {
            initialState = FakeState()
            storeName = "MeteorConfigs Test Store Name"
            middlewares = listOf(fakeMiddleware())
            reducer = FakeReducer
            mainDispatcher = Dispatchers.Main
            ioDispatcher = Dispatchers.IO
        }
    }

    @Test
    fun `check meteor configs is initialised correctly`() {
        assertEquals(configs.initialState, FakeState())
    }

    @Test
    fun `test meteor configs store name existence`() {
        val storeName = "MeteorConfigs Test Store Name"

        assertEquals(configs.storeName, storeName)
    }

    @Test
    fun `test meteor configurations with main dispatcher`() {
        val dispatcher: MainCoroutineDispatcher = Dispatchers.Main

        assertEquals(dispatcher, configs.mainDispatcher)
    }

    @Test
    fun `test meteor configurations with io dispatcher`() {
        val dispatcher = Dispatchers.IO

        assertEquals(dispatcher, configs.ioDispatcher)
    }
}
