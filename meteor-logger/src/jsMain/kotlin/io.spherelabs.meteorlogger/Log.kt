package io.spherelabs.meteorlogger

public actual fun info(message: String, tag: String) {}

public actual fun debug(message: String, tag: String) {}

public actual fun failure(message: String, throwable: Throwable?, tag: String) {}

public actual fun log(previousState: Any, newState: Any, wish: Any) {}

public actual fun log(text: Any) {}

public actual fun logTest(previousState: Any, newState: Any, wish: Any) {}

public actual fun logTest(wish: Any) {}

public actual fun logTestEffect(effect: Any) {}
