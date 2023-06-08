package io.spherelabs.meteor.dsl

import io.spherelabs.meteor.annotation.MeteorDsl
import io.spherelabs.meteor.annotation.MeteorInternal
import io.spherelabs.meteor.configs.Change
import io.spherelabs.meteor.reducer.Reducer
import kotlin.reflect.KClass

@MeteorInternal
@MeteorDsl
class ReducerBuilder<State : Any, Wish : Any, Effect : Any> internal constructor() {

    val reducers: MutableMap<KClass<out Wish>, ToReducerContext<State, Wish, Effect>> = mutableMapOf()

    private var currentState: State? = null

    inline fun <reified W : Wish> on(noinline action: State.(W) -> Change<State, Effect>) {
        reducers[W::class] = action as State.(Wish) -> Change<State, Effect>
    }

    fun <State : Any> transition(
        action: () -> State,
    ): Change<State, Effect> {
        return Change(state = action())
    }

    fun build(): Reducer<State, Wish, Effect> {
        return Reducer { state, wish ->
            reducers[wish::class]?.let {
                it(state, wish)
            } ?: throw IllegalArgumentException("No reducer found for wish: $wish")
        }
    }

}


typealias ToReducerContext <State, Wish, Effect> = (State, Wish) -> Change<State, Effect>

