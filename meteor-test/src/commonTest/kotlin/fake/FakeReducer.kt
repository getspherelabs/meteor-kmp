package fake

import io.spherelabs.meteor.configs.Change
import io.spherelabs.meteor.extension.change
import io.spherelabs.meteor.extension.route
import io.spherelabs.meteor.extension.unexpected
import io.spherelabs.meteor.reducer.Reducer

object FakeReducer : Reducer<FakeState, FakeWish, FakeEffect> {

    override fun reduce(currentState: FakeState, currentWish: FakeWish): Change<FakeState, FakeEffect> {
        return when (currentWish) {
            FakeWish.Decrement -> {
                change {
                    state { currentState.copy(count = currentState.count - 1) }
                }
            }

            FakeWish.Increment -> {
                change {
                    state { currentState.copy(count = currentState.count + 1) }
                }
            }

            FakeWish.Route.Home -> {
                route {
                    FakeEffect.Route.Home
                }
            }

            else -> {
                unexpected { currentState }
            }
        }
    }
}
