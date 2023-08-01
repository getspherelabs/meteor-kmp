package io.spherelabs.meteor.dsl

import io.spherelabs.meteor.annotation.MeteorDsl
import io.spherelabs.meteor.annotation.MeteorInternal
import io.spherelabs.meteor.configs.Change
import io.spherelabs.meteor.reducer.Reducer
import kotlin.reflect.KClass

@MeteorInternal
@MeteorDsl
public class ReducerBuilder<State : Any, Wish : Any, Effect : Any> internal constructor() {

    public val reducers: MutableMap<KClass<out Wish>, ToReducerContext<State, Wish, Effect>> = mutableMapOf()

    private var currentState: State? = null

    public inline fun <reified W : Wish> on(noinline action: State.(W) -> Change<State, Effect>) {
        reducers[W::class] = action as State.(Wish) -> Change<State, Effect>
    }

    public fun <State : Any> transition(
        action: () -> State
    ): Change<State, Effect> {
        return Change(state = action())
    }

    public fun build(): Reducer<State, Wish, Effect> {
        return object : Reducer<State, Wish, Effect> {
            override fun reduce(currentState: State, currentWish: Wish): Change<State, Effect> {
                return reducers[currentWish::class]?.let {
                    it(currentState, currentWish)
                } ?: throw IllegalArgumentException("No reducer found for wish: $currentWish")
            }
        }
    }
}

public typealias ToReducerContext<State, Wish, Effect> = (State, Wish) -> Change<State, Effect>
