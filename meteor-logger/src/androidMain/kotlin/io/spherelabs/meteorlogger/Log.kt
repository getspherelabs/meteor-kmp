package io.spherelabs.meteorlogger

import android.util.Log

public actual fun info(message: String, tag: String) {
    Log.i(tag, message)
}

public actual fun debug(message: String, tag: String) {
    Log.d(tag, message)
}

public actual fun failure(message: String, throwable: Throwable?, tag: String) {
    Log.e(tag, message, throwable)
}

public actual fun log(previousState: Any, newState: Any, wish: Any) {
    println("Previous state: $previousState")
    println("Received wish: ${wish.format()}")
    println("New state: $newState")
}

public actual fun log(text: Any) {
    println(text)
}

public actual fun logTest(previousState: Any, newState: Any, wish: Any) {
    if (BuildConfig.DEBUG) {
        println("Previous state: $previousState")
        println("Received wish: ${wish.format()}")
        println("New state: $newState")
    }
}

public actual fun logTest(wish: Any) {
    println("Processing a new wish from middleware: ${wish.format()}")
}

public actual fun logTestEffect(effect: Any) {
    println("Sending a new effect: ${effect.format()}")
}
