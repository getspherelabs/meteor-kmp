package io.spherelabs.meteor.middleware

/**
 * [Middleware] sits between the source of wishes and the store,
 * allowing for additional processing or side effects to occur before and after the state is updated.
 */
interface Middleware<Wish : Any> {
    suspend fun process(wish: Wish, next: suspend (Wish) -> Unit)
}
