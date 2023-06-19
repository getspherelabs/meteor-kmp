package io.spherelabs.meteor.viewmodel

import io.spherelabs.meteor.configs.MeteorConfigs
import io.spherelabs.meteor.store.Store
import io.spherelabs.meteor.store.createMeteor

/**
 *
 */
public fun <State : Any, Wish : Any, Effect : Any> CommonViewModel<State, Wish, Effect>.createMeteor(
    configs: MeteorConfigs<State, Wish, Effect>
): Store<State, Wish, Effect> {
    return viewModelScope.createMeteor(configs)
}
