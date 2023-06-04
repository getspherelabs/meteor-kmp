package io.spherelabs.meteor.middleware

import io.spherelabs.meteor.configs.To

/**
 * [Middleware] sits between the source of wishes and the store,
 * allowing for additional processing or side effects to occur before and after the state is updated.
 */
interface Middleware<State : Any, Wish : Any, Effect : Any> {
    suspend fun process(effect: To<State, Effect>, wish: Wish, next: suspend (Wish) -> Unit)
}
