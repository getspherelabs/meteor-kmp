package io.spherelabs.meteor.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * A [Store] is responsible for managing the state of an application,
 * processing wishes (actions/intents), and producing effects.
 */
interface Store<State : Any, Wish : Any, Effect : Any> {
    /**
     * A [state] represents state of the store.
     */
    val state: StateFlow<State>

    /**
     * A [currentState] represents the current state of the store.
     */
    val currentState: State

    /**
     * A [effect] represents the flow of effects produced by the store.
     */
    val effect: Flow<Wish>

    /**
     * A [wish] function is used to send a wish (action/intent) to the store for processing.
     * [Wish] represent the intent to change the state of the application.
     */
    suspend fun wish(wish: Wish)
}
