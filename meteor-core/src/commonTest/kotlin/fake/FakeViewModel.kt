package fake

import io.spherelabs.meteor.configs.MeteorConfigs
import io.spherelabs.meteor.store.Store
import io.spherelabs.meteor.viewmodel.CommonViewModel
import io.spherelabs.meteor.viewmodel.createMeteor

class FakeViewModel : CommonViewModel<FakeCountState, FakeCountWish, FakeCountEffect>() {

    override val store: Store<FakeCountState, FakeCountWish, FakeCountEffect> = createMeteor(
        configs = MeteorConfigs.build {
            initialState = FakeCountState()
            storeName = "fake.FakeViewModel"
            reducer = FakeCountReducer
            middlewares = listOf(countMiddleware(), loggingMiddleware())
        }
    )
}
