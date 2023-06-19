package io.spherelabs.countercompose.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import io.spherelabs.meteor.configs.MeteorConfigs
import io.spherelabs.meteor.store.Store
import io.spherelabs.meteor.viewmodel.CommonViewModel
import io.spherelabs.meteor.viewmodel.createMeteor
import javax.inject.Inject

@HiltViewModel
class CountViewModel @Inject constructor() : CommonViewModel<CountState, CountWish, CountEffect>() {

    override val store: Store<CountState, CountWish, CountEffect> = createMeteor(
        configs = MeteorConfigs.build {
            initialState = CountState.Empty
            reducer = CountReducer
            storeName = "Count ViewModel"
            middleware = CountMiddleware
        }
    )
}
