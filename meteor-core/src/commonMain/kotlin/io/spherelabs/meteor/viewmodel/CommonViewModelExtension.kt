package io.spherelabs.meteor.viewmodel

import io.spherelabs.meteor.configs.MeteorConfigs
import io.spherelabs.meteor.store.Store
import io.spherelabs.meteor.store.createMeteor
import kotlinx.coroutines.CoroutineScope

fun <State: Any, Wish: Any, Effect: Any> CommonViewModel<State, Wish, Effect>.container(
    configs: MeteorConfigs<State, Wish, Effect>,
    scope: CoroutineScope
): Store<State, Wish, Effect> {
    return scope.createMeteor(configs)
}
