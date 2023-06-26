package io.spherelabs.meteorlogger

/**
 * Logs an informational message with tag.
 */
public expect fun info(message: String, tag: String)

/**
 * Logs a debug message with tag.
 */
public expect fun debug(message: String, tag: String)

/**
 * Logs a failure message with tag.
 */
public expect fun failure(message: String, throwable: Throwable? = null, tag: String)

/**
 * Logs the previous state, new state and wish for a specific operation.
 */
public expect fun log(previousState: Any, newState: Any, wish: Any)

/**
 * Logs the current text.
 */
public expect fun log(text: Any)

/**
 * Logs the previous state, new state and wish for the test.
 */
public expect fun logTest(previousState: Any, newState: Any, wish: Any)

/**
 * Logs the only specific wish without any state information for the test.
 */
public expect fun logTest(wish: Any)

/**
 * Logs the only specific effect without any state information for the test.
 */
public expect fun logTestEffect(effect: Any)
