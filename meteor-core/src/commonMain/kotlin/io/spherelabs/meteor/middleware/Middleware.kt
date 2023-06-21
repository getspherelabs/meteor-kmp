package io.spherelabs.meteor.middleware

/**
 * [Middleware] sits between the source of wishes and the store,
 * allowing for additional processing or side effects to occur before and after the state is updated.
 */
public interface Middleware<State : Any, Wish : Any> {
    public suspend fun process(state: State, wish: Wish, next: suspend (Wish) -> Unit)
}
