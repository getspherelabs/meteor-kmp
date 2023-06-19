package io.spherelabs.meteor.dsl

import io.spherelabs.meteor.annotation.MeteorDsl
import io.spherelabs.meteor.annotation.MeteorInternal

@MeteorInternal
@MeteorDsl
public class ConfigDslBuilder<State : Any> internal constructor() {
    public var initialState: State? = null
    public var name: String? = null
}
