package fake

import io.spherelabs.meteor.configs.MeteorConfigs
import io.spherelabs.meteor.store.Store
import io.spherelabs.meteor.store.createMeteor
import io.spherelabs.meteor.viewmodel.CommonViewModel
import io.spherelabs.meteor.viewmodel.createViewModelScope

class FakeViewModel: CommonViewModel<FakeCountState, FakeCountWish, FakeCountEffect>() {

    override val host: Store<FakeCountState, FakeCountWish, FakeCountEffect>
        get() = createMeteor(
            configs = MeteorConfigs.build {
                initialState = FakeCountState()
                storeName = "fake.FakeViewModel"
                reducer = FakeCountReducer
                middleware = FakeCountMiddleware
            },
            mainScope = createViewModelScope()
    )


}
