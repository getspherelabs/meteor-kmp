package configs

import io.spherelabs.meteor.configs.MeteorConfigs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MeteorConfigsTest {

    private lateinit var configs: MeteorConfigs<FakeConfigState>

    @BeforeTest
    fun setup() {
        configs = MeteorConfigs.build {
            initialState = FakeConfigState()
            storeName = "MeteorConfigs Test Store Name"
            mainDispatcher = Dispatchers.Main
            ioDispatcher = Dispatchers.IO
        }
    }

    @Test
    fun `check meteor configs is initialised correctly`() {
        assertEquals(configs.initialState, FakeConfigState())
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
