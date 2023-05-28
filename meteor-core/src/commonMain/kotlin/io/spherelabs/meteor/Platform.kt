package io.spherelabs.meteor

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform