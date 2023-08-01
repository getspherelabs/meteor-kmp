package fake

import io.spherelabs.meteor.configs.Change
import io.spherelabs.meteor.extension.change
import io.spherelabs.meteor.reducer.Reducer

object FakeCountReducer : Reducer<FakeCountState, FakeCountWish, FakeCountEffect> {

    override fun reduce(currentState: FakeCountState, currentWish: FakeCountWish): Change<FakeCountState, FakeCountEffect> {
        return when (currentWish) {
            FakeCountWish.Decrease -> {
                change {
                    state { currentState.copy(count = currentState.count - 1) }
                }
            }

            FakeCountWish.Increase -> {
                change {
                    state { currentState.copy(count = currentState.count + 1) }
                }
            }

            FakeCountWish.Reset -> {
                change {
                    state { currentState.copy(count = 0) }
                }
            }

            FakeCountWish.ZeroValue -> {
                change {
                    effect { FakeCountEffect.Failure("The value is zero") }
                }
            }
        }
    }
}
