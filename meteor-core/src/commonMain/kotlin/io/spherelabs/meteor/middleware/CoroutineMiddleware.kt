package io.spherelabs.meteor.middleware

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.supervisorScope

public interface CoroutineMiddleware<State : Any, Wish : Any> : Middleware<State, Wish> {

    override suspend fun process(state: State, wish: Wish, next: suspend (Wish) -> Unit) {
        supervisorScope { handle(state, wish, next) }
    }

    public suspend fun CoroutineScope.handle(state: State, wish: Wish, next: suspend (Wish) -> Unit)
}
