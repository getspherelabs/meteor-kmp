package io.spherelabs.meteor.dsl

import io.spherelabs.meteor.annotation.MeteorDsl
import io.spherelabs.meteor.annotation.MeteorInternal
import io.spherelabs.meteor.middleware.Middleware

@MeteorInternal
@MeteorDsl
public class MiddlewareDslBuilder<State : Any, Wish : Any> internal constructor() {

    public var process: MiddlewareContext<State, Wish>? = null

    public fun on(actionBlock: MiddlewareContext<State, Wish>) {
        process = actionBlock
    }

    public fun build(): Middleware<State, Wish> = object : Middleware<State, Wish> {
        override suspend fun process(state: State, wish: Wish, next: suspend (Wish) -> Unit) {
            this@MiddlewareDslBuilder.process?.invoke(state, wish, next)
        }
    }
}

public typealias MiddlewareContext<State, Wish> = suspend (State, Wish, suspend (Wish) -> Unit) -> Unit
