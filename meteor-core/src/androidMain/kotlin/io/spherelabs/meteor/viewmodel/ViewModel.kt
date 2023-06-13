package io.spherelabs.meteor.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import androidx.lifecycle.ViewModel as AndroidXViewModel

actual open class ViewModel actual constructor() : AndroidXViewModel() {

    protected actual val viewModelScope: CoroutineScope = createViewModelScope()

    public actual override fun onCleared() {
        super.onCleared()

        viewModelScope.cancel()
    }
}
