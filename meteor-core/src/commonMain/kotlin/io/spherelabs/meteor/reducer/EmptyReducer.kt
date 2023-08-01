package io.spherelabs.meteor.reducer

import io.spherelabs.meteor.configs.Change
import io.spherelabs.meteor.extension.unexpected

public fun <State : Any, Wish : Any, Effect : Any> emptyReducer(): Reducer<State, Wish, Effect> = object : Reducer<State, Wish, Effect> {

    override fun reduce(currentState: State, currentWish: Wish): Change<State, Effect> {
        return unexpected { currentState }
    }
}
