package io.spherelabs.counterkmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform