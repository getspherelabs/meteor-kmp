package io.spherelabs.meteorviewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
var createViewModelScope: () -> CoroutineScope = {
    CoroutineScope(Dispatchers.Main + SupervisorJob())
}
