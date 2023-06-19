package io.spherelabs.countercompose.presentation

import io.spherelabs.meteor.configs.Change
import io.spherelabs.meteor.reducer.Reducer
import io.spherelabs.meteor.reducer.effect
import io.spherelabs.meteor.reducer.expect

object CountReducer : Reducer<CountState, CountWish, CountEffect> {

    override fun reduce(state: CountState, wish: CountWish): Change<CountState, CountEffect> {
        return when (wish) {
            CountWish.Decrease -> {
                expect { state.copy(count = state.count - 1) }
            }
            CountWish.Increase -> {
                expect { state.copy(count = state.count + 1) }
            }
            CountWish.Reset -> expect {
                state.copy(count = 0)
            }
            is CountWish.Toast -> {
                effect {
                    CountEffect.Failure(wish.message)
                }
            }
        }
    }
}
