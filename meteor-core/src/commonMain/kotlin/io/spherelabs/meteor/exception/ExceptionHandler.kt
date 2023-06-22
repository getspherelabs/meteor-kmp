package io.spherelabs.meteor.exception

public interface ExceptionHandler {
    public fun handle(exception: Exception): Boolean
}
