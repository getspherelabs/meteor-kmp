package io.spherelabs.counterkmm

import io.spherelabs.meteor.configs.MeteorConfigs
import io.spherelabs.meteor.store.Store
import io.spherelabs.meteor.store.createMeteor
import io.spherelabs.meteor.viewmodel.CommonViewModel

class CounterViewModel : CommonViewModel<CounterState, CounterWish, CounterEffect>() {

    override val host: Store<CounterState, CounterWish, CounterEffect> = createMeteor(
        configs = MeteorConfigs.build {
            initialState = CounterState()
            reducer = CounterReducer
            middleware = CounterMiddleware
        },
        mainScope = viewModelScope
    )
}
