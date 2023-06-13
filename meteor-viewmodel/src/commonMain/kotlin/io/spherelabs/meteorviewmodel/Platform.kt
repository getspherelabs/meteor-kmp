package io.spherelabs.meteorviewmodel

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform