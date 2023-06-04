package io.spherelabs.meteor.dsl

import io.spherelabs.meteor.annotation.MeteorDsl
import io.spherelabs.meteor.annotation.MeteorInternal
import io.spherelabs.meteor.configs.To
import io.spherelabs.meteor.middleware.Middleware

@MeteorInternal
@MeteorDsl
class MiddlewareDslBuilder<State : Any, Wish : Any, Effect : Any> internal constructor() {

    var process: MiddlewareContext<State, Wish, Effect>? = null

    fun on(actionBlock: MiddlewareContext<State, Wish, Effect>) {
        process = actionBlock
    }


    fun build(): Middleware<State, Wish, Effect> = object : Middleware<State, Wish, Effect> {
        override suspend fun process(effect: To<State, Effect>, wish: Wish, next: suspend (Wish) -> Unit) {
            this@MiddlewareDslBuilder.process?.invoke(effect, wish, next)
        }
    }
}


typealias MiddlewareContext<State, Wish, Effect> = suspend (To<State, Effect>, Wish, suspend (Wish) -> Unit) -> Unit

