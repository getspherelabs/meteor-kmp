package fake

import io.spherelabs.meteor.configs.MeteorConfigs
import io.spherelabs.meteor.store.Store
import io.spherelabs.meteor.store.createMeteor
import io.spherelabs.meteor.viewmodel.CommonViewModel
import io.spherelabs.meteor.viewmodel.createViewModelScope
import io.spherelabs.meteorviewmodel.flow.NonNullCommonFlow
import io.spherelabs.meteorviewmodel.flow.NotNullCommonStateFlow
import io.spherelabs.meteorviewmodel.flow.asCommonFlow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeViewModel : CommonViewModel<FakeCountState, FakeCountWish, FakeCountEffect>() {

    override val host: Store<FakeCountState, FakeCountWish, FakeCountEffect> = viewModelScope.createMeteor(
        configs = MeteorConfigs.build {
            initialState = FakeCountState()
            storeName = "fake.FakeViewModel"
            reducer = FakeCountReducer
            middleware = FakeCountMiddleware
        }
    )

    val effect: NonNullCommonFlow<FakeCountEffect> = host.effect.asCommonFlow()
    val state: NotNullCommonStateFlow<FakeCountState> = host.state.asCommonFlow()

}
