object Version {
    const val coroutine = "1.7.1"
    const val viewModel = "2.2.0"
}

object Libs {
    object Coroutine {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutine}"
    }
    object Android {
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.viewModel}"
    }
}