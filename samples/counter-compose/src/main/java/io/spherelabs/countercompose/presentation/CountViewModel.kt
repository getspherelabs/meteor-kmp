package io.spherelabs.countercompose.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import io.spherelabs.meteor.configs.Change
import io.spherelabs.meteor.configs.MeteorConfigs
import io.spherelabs.meteor.extension.effect
import io.spherelabs.meteor.extension.expect
import io.spherelabs.meteor.reducer.Reducer
import io.spherelabs.meteor.store.Store
import io.spherelabs.meteor.viewmodel.CommonViewModel
import io.spherelabs.meteor.viewmodel.createMeteor
import javax.inject.Inject

@HiltViewModel
class CountViewModel @Inject constructor() : CommonViewModel<CountState, CountWish, CountEffect>() {

    override val store: Store<CountState, CountWish, CountEffect> = createMeteor(
        configs = MeteorConfigs.build {
            initialState = CountState.Empty
            reducer = countReducer()
            storeName = "Count ViewModel"
            middlewares = listOf(countMiddleware())
        }
    )
}

fun countReducer() = object : Reducer<CountState, CountWish, CountEffect> {
    override fun reduce(currentState: CountState, currentWish: CountWish): Change<CountState, CountEffect> {
        return when (currentWish) {
            CountWish.Decrease -> {
                expect { currentState.copy(count = currentState.count - 1) }
            }
            CountWish.Increase -> {
                expect { currentState.copy(count = currentState.count + 1) }
            }
            CountWish.Reset -> expect {
                currentState.copy(count = 0)
            }
            is CountWish.Toast -> {
                effect {
                    CountEffect.Failure(currentWish.message)
                }
            }
        }
    }
}
