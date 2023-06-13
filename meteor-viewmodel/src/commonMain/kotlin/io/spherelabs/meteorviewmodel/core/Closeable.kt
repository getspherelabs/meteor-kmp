package io.spherelabs.meteorviewmodel.core

/**
 * A [Closeable] manages resources that need to be closed or released when the ViewModel is no longer needed.
 */
public expect fun interface Closeable {
    /**
     * Closes underlying resources
     */
    public fun close()
}
