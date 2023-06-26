package io.spherelabs.meteor

import io.spherelabs.meteorlogger.logTest
import kotlinx.coroutines.flow.Flow

public interface Interceptor<Type : Any> {
    public fun <Type> handle(value: Flow<Type>)
}

internal class LoggerInterceptor<State : Any> : Interceptor<State> {
    override fun <Type> handle(value: Flow<Type>) {
        logTest(value)
    }
}
