package fake

import io.spherelabs.meteor.configs.Change
import io.spherelabs.meteor.extension.expect
import io.spherelabs.meteor.extension.route
import io.spherelabs.meteor.extension.unexpected
import io.spherelabs.meteor.reducer.Reducer

object FakeReducer : Reducer<FakeState, FakeWish, FakeEffect> {

    override fun reduce(state: FakeState, wish: FakeWish): Change<FakeState, FakeEffect> {
        return when (wish) {
            FakeWish.Decrement -> {
                expect {
                    state.copy(count = state.count - 1)
                }
            }

            FakeWish.Increment -> {
                expect {
                    state.copy(count = state.count + 1)
                }
            }

            FakeWish.Route.Home -> {
                route {
                    FakeEffect.Route.Home
                }
            }

            else -> {
                unexpected { state }
            }
        }
    }
}
