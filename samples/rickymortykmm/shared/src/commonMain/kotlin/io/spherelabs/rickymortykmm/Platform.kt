package io.spherelabs.rickymortykmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
