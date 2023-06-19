package io.spherelabs.meteortest.store

import io.spherelabs.meteor.configs.MeteorConfigs
import io.spherelabs.meteor.store.Store
import io.spherelabs.meteor.store.createMeteor
import kotlinx.coroutines.CoroutineScope

public fun <State : Any, Wish : Any, Effect : Any> createTestStore(
    testConfigs: MeteorConfigs<State, Wish, Effect>,
    testScope: CoroutineScope
): Store<State, Wish, Effect> {
    return createMeteor(
        configs = testConfigs,
        mainScope = testScope
    )
}

public fun <State : Any, Wish : Any, Effect : Any> CoroutineScope.createTestStore(
    configs: MeteorConfigs<State, Wish, Effect>
): Store<State, Wish, Effect> {
    return createTestStore(
        testConfigs = configs,
        testScope = this
    )
}
