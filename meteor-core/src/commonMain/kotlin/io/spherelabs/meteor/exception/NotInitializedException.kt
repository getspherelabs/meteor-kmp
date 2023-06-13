package io.spherelabs.meteor.exception

class NotInitializedException : RuntimeException {
    constructor()
    constructor(message: String = "State is not initialized.")
}
