package fake

import io.spherelabs.meteor.reducer.Reducer

object FakeReducer : Reducer<FakeState, FakeWish> {

    override fun reduce(state: FakeState, wish: FakeWish): FakeState {
        return when (wish) {
            FakeWish.Decrement -> {
                state.copy(count = state.count - 1)
            }

            FakeWish.Increment -> {
                state.copy(count = state.count + 1)
            }
        }
    }
}
