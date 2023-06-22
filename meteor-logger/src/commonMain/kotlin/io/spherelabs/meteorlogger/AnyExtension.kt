package io.spherelabs.meteorlogger

internal fun Any.format(): String {
    val input = this.toString()
    val className = input.substringAfterLast('.')
    return className.substringBefore('@').substringAfterLast('$')
}
