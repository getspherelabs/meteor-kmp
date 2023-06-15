package io.spherelabs.meteor.dsl

import io.spherelabs.meteor.annotation.MeteorDsl
import io.spherelabs.meteor.annotation.MeteorInternal

@MeteorInternal
@MeteorDsl
class ConfigDslBuilder<State : Any> internal constructor() {
    var initialState: State? = null
    var name: String? = null
}
