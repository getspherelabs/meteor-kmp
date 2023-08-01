package io.spherelabs.meteorviewmodel.core

public inline val Boolean.toInt: Int get() = if (this) 1 else 0
