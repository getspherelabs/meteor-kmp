package io.spherelabs.meteor.interceptor

import io.spherelabs.meteorlogger.format

public sealed interface Message<State : Any, Wish : Any, Effect : Any> {
    public val storeName: String

    public class OnStateTransition<State : Any, Wish : Any, Effect : Any>(
        override val storeName: String,
        private val state: State,
        private val transition: StateTransition
    ) : Message<State, Wish, Effect> {
        override fun toString(): String {
            return when (transition) {
                StateTransition.CREATED -> "↗ Created state: $state"
                StateTransition.PREVIOUS -> "↑ Previous state: $state"
                StateTransition.CURRENT -> "↓ Current state: $state"
                StateTransition.NEW -> "⇅ New state is $state"
            }
        }
    }

    public class ReceivedWish<State : Any, Wish : Any, Effect : Any>(
        override val storeName: String,
        private val currentWish: Wish
    ) : Message<State, Wish, Effect> {
        override fun toString(): String {
            return "↘ Received wish: ${currentWish.format()}"
        }
    }

    public class NewWish<State : Any, Wish : Any, Effect : Any>(
        override val storeName: String,
        private val currentWish: Wish
    ) : Message<State, Wish, Effect> {
        override fun toString(): String {
            return "↖ New sending wish: ${currentWish.format()}"
        }
    }
    public class ReceivedEffect<State : Any, Wish : Any, Effect : Any>(
        override val storeName: String,
        private val effect: Effect
    ) : Message<State, Wish, Effect> {
        override fun toString(): String {
            return "↘ Received effect: $effect"
        }
    }

    public class NewEffect<State : Any, Wish : Any, Effect : Any>(
        override val storeName: String,
        private val effect: Effect
    ) : Message<State, Wish, Effect> {
        override fun toString(): String {
            return "↖ New sending effect: $effect"
        }
    }
}
