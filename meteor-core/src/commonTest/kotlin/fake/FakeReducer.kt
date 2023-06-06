package fake

import io.spherelabs.meteor.configs.To
import io.spherelabs.meteor.reducer.Reducer
import io.spherelabs.meteor.reducer.expect
import io.spherelabs.meteor.reducer.unexpected

object FakeReducer : Reducer<FakeState, FakeWish, FakeEffect> {

    override fun reduce(state: FakeState, wish: FakeWish): To<FakeState, FakeEffect> {
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

            else -> {
                unexpected()
            }
        }
    }
}
