package io.spherelabs.counterkmm

import io.spherelabs.meteor.configs.Change
import io.spherelabs.meteor.reducer.Reducer
import io.spherelabs.meteor.reducer.effect
import io.spherelabs.meteor.reducer.expect

object CounterReducer : Reducer<CounterState, CounterWish, CounterEffect> {

    override fun reduce(state: CounterState, wish: CounterWish): Change<CounterState, CounterEffect> {
        return when (wish) {
            CounterWish.Decrease -> {
                expect {
                    state.copy(count = state.count - 1)
                }
            }
            CounterWish.Increase -> {
                expect { state.copy(count = state.count + 1) }
            }
            CounterWish.Reset -> {
                expect { state.copy(count = 0) }
            }
            CounterWish.Info -> {
                effect {
                    CounterEffect.Failure("The value is removed.")
                }
            }
        }
    }
}
