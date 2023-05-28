package io.spherelabs.meteor.configs

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
interface MeteorConfigs<State : Any> {
    val initialState: State
    val storeName: String
    val mainDispatcher: CoroutineDispatcher
    val ioDispatcher: CoroutineDispatcher

    /**
     * A [Builder] class for constructing instance of [MeteorConfigs].
     */
    data class Builder<State : Any>(
        var initialState: State? = null,
        var storeName: String? = null,
        var mainDispatcher: CoroutineDispatcher = Dispatchers.Default,
        var ioDispatcher: CoroutineDispatcher = Dispatchers.Default
    ) {
        companion object {
            /**
             * A [build] function takes a configuration block to set properties of the builder class and
             * constructs a fully configured MeteorConfigs object using the provided settings.
             */
            inline fun <State : Any> build(
                block: Builder<State>.() -> Unit
            ): MeteorConfigs<State> {
                val builder = Builder<State>().apply(block)

                return object : MeteorConfigs<State> {
                    override val initialState: State = checkNotNull(builder.initialState) {
                        "State is not initialised"
                    }
                    override val storeName: String = checkNotNull(builder.storeName) {
                        "Store name is not initialised"
                    }
                    override val mainDispatcher: CoroutineDispatcher = builder.mainDispatcher
                    override val ioDispatcher: CoroutineDispatcher = builder.ioDispatcher
                }
            }
        }
    }
}