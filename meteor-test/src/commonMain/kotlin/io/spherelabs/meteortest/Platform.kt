package io.spherelabs.meteortest

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform