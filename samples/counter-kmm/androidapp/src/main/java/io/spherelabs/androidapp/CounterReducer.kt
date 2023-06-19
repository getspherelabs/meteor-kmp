package io.spherelabs.androidapp

import io.spherelabs.counterkmm.CounterEffect
import io.spherelabs.counterkmm.CounterState
import io.spherelabs.counterkmm.CounterWish
import io.spherelabs.meteor.configs.Change
import io.spherelabs.meteor.reducer.Reducer
import io.spherelabs.meteor.reducer.effect
import io.spherelabs.meteor.reducer.expect
import io.spherelabs.meteor.reducer.unexpected

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
            else -> {
                unexpected()
            }
        }
    }
}
