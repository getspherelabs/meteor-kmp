package io.spherelabs.meteor.dsl

import io.spherelabs.meteor.annotation.MeteorDsl
import io.spherelabs.meteor.annotation.MeteorInternal
import io.spherelabs.meteor.reducer.Reducer
import kotlin.reflect.KClass

@MeteorInternal
@MeteorDsl
class ReducerBuilder<State : Any, Wish : Any> internal constructor() {

    val reducers: MutableMap<KClass<out Wish>, ComplexReducerContext<State, Wish>> = mutableMapOf()

    inline fun <reified W : Wish> on(noinline action: State.(Wish) -> State) {
        reducers[W::class] = { state, wish ->
            if (wish is W) {
                action(state, wish)
            } else {
                state
            }
        }
    }

    fun <State> transition(state : State): State {
        return state
    }

    internal fun build(): Reducer<State, Wish> {
        return Reducer { state, wish ->
            reducers[wish::class]?.invoke(state, wish) ?: state
        }
    }

}

typealias ComplexReducerContext<State, Wish> = (State, Wish) -> State

