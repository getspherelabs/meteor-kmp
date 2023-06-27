package io.spherelabs.meteor.interceptor

import io.spherelabs.meteorlogger.log

public interface Interceptor<State : Any, Wish : Any, Effect : Any> {
    public fun process(message: Message<State, Wish, Effect>)
}

public class LoggingInterceptor<State : Any, Wish : Any, Effect : Any> : Interceptor<State, Wish, Effect> {
    override fun process(message: Message<State, Wish, Effect>) {
        log(message)
    }
}
