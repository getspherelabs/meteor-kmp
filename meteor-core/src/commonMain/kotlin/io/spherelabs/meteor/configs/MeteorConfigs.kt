package io.spherelabs.meteor.configs

import io.spherelabs.meteor.middleware.Middleware
import io.spherelabs.meteor.reducer.Reducer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * A [MeteorConfigs] represents the configuration for a Store implementation,
 * including initial state, store name, and dispatchers for different asynchronous operations.
 *
 * Here is the implementation of the `MeteorConfigs`:
 *
 * ```kotlin
 * class DefaultStore<State: Any, Wish: Any, Effect: Any> (
 *    private val configs: MeteorConfigs<State>
 * ): Store<State, Wish, Effect>
 *
 * fun createMeteor<State, Wish, Effect>(
 *      configs = MeteorConfigs
 * ): Store<State, Wish, Effect> {
 *      return DefaultStore<State, Wish, Effect> (
 *                  /** Code **/
 *      )
 * ```
 *
 * Usage:
 * ```kotlin
 * createMeteor<State, Wish, Effect>(
 *    configs = MeteorConfigs.create {
 *              initialState = currentInitialState,
 *              storeName = "Meteor DSL Store" }
 * )
 * ```
 *
 */
public interface MeteorConfigs<State : Any, Wish : Any, Effect : Any> {
    public val initialState: State
    public val storeName: String
    public val mainDispatcher: CoroutineDispatcher
    public val ioDispatcher: CoroutineDispatcher
    public val reducer: Reducer<State, Wish, Effect>
    public val middleware: Middleware<Wish>

    /**
     * A [Builder] class for constructing instance of [MeteorConfigs].
     */
    public data class Builder<State : Any, Wish : Any, Effect : Any>(
        var initialState: State? = null,
        var storeName: String? = null,
        var mainDispatcher: CoroutineDispatcher = Dispatchers.Default,
        var ioDispatcher: CoroutineDispatcher = Dispatchers.Default,
        var reducer: Reducer<State, Wish, Effect>? = null,
        var middleware: Middleware<Wish>? = null
    )

    public companion object {
        /**
         * A [build] function takes a configuration block to set properties of the builder class and
         * constructs a fully configured MeteorConfigs object using the provided settings.
         */
        public inline fun <State : Any, Wish : Any, Effect : Any> build(
            block: Builder<State, Wish, Effect>.() -> Unit
        ): MeteorConfigs<State, Wish, Effect> {
            val builder = Builder<State, Wish, Effect>().apply(block)

            return object : MeteorConfigs<State, Wish, Effect> {
                override val initialState: State = checkNotNull(builder.initialState) {
                    "State is not initialized."
                }
                override val storeName: String = checkNotNull(builder.storeName) {
                    "Store name is not initialized."
                }
                override val mainDispatcher: CoroutineDispatcher = builder.mainDispatcher
                override val ioDispatcher: CoroutineDispatcher = builder.ioDispatcher
                override val reducer: Reducer<State, Wish, Effect> = checkNotNull(builder.reducer) {
                    "Reducer is not initialized."
                }
                override val middleware: Middleware<Wish> = checkNotNull(builder.middleware) {
                    "Middleware is not initialized."
                }
            }
        }
    }
}
