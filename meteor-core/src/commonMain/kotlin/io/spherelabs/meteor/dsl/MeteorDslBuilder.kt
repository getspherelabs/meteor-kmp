package io.spherelabs.meteor.dsl

import io.spherelabs.meteor.annotation.MeteorDsl
import io.spherelabs.meteor.configs.MeteorConfigs
import io.spherelabs.meteor.middleware.Middleware
import io.spherelabs.meteor.reducer.Reducer
import io.spherelabs.meteor.store.Store
import io.spherelabs.meteor.store.createMeteor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@MeteorDsl
public class MeteorDslBuilder<State : Any, Wish : Any, Effect : Any> internal constructor() {

    private var currentState: State? = null
    private var storeName: String? = null

    private val mainScope: CoroutineScope = CoroutineScope(SupervisorJob())

    private var reducer: Reducer<State, Wish, Effect>? = null
    private var middlewares: List<Middleware<State, Wish>> = emptyList()

    public fun config(block: ConfigDslBuilder<State>.() -> Unit) {
        ConfigDslBuilder<State>().also {
            block(it)
            this.currentState = it.initialState
            this.storeName = it.name
        }
    }

    public fun reducer(block: ReducerBuilder<State, Wish, Effect>.() -> Unit) {
        ReducerBuilder<State, Wish, Effect>().also {
            block(it)
            this.reducer = it.build()
        }
    }

    public fun middleware(
        block: MiddlewareDslBuilder<State, Wish>.() -> Unit
    ) {
        MiddlewareDslBuilder<State, Wish>().also {
            block(it)
            this.middlewares = listOf(it.build())
        }
    }

    public fun build(): Store<State, Wish, Effect> {
        return mainScope.createMeteor(
            configs = MeteorConfigs.build {
                initialState = this@MeteorDslBuilder.currentState
                storeName = if (this@MeteorDslBuilder.storeName.isNullOrBlank()) {
                    "Meteor DSL Builder"
                } else {
                    this@MeteorDslBuilder.storeName
                }
                reducer = checkNotNull(this@MeteorDslBuilder.reducer) {
                    "Reducer is not initialized. Please, check the reducer instance!"
                }
                middlewares = this@MeteorDslBuilder.middlewares
            }
        )
    }
}

public fun <State : Any, Wish : Any, Effect : Any> meteor(
    block: MeteorDslBuilder<State, Wish, Effect>.() -> Unit
): Store<State, Wish, Effect> {
    return MeteorDslBuilder<State, Wish, Effect>().also(block).build()
}
