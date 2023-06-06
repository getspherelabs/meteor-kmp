package io.spherelabs.meteor.dsl

import io.spherelabs.meteor.annotation.MeteorDsl
import io.spherelabs.meteor.annotation.MeteorInternal
import io.spherelabs.meteor.middleware.Middleware

@MeteorInternal
@MeteorDsl
class MiddlewareDslBuilder<Wish : Any> internal constructor() {

    var process: MiddlewareContext<Wish>? = null

    fun on(actionBlock: MiddlewareContext<Wish>) {
        process = actionBlock
    }


    fun build(): Middleware<Wish> = object : Middleware<Wish> {
        override suspend fun process(wish: Wish, next: suspend (Wish) -> Unit) {
            this@MiddlewareDslBuilder.process?.invoke(wish, next)
        }
    }
}


typealias MiddlewareContext<Wish> = suspend (Wish, suspend (Wish) -> Unit) -> Unit

