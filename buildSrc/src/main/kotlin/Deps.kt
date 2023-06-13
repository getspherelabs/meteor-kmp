object Version {
    const val coroutine = "1.7.1"
    const val viewModel = "2.2.0"
    const val ktlint = "10.3.0"
    const val coroutineTest = "1.7.1"
    const val dokka = "1.8.10"
    const val lifecycle = "2.6.0"
    const val stately = "2.0.0"
}

object Libs {
    object Coroutine {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutine}"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutineTest}"
    }

    object Android {
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycle}"
    }

    object Ios {
        const val stately = "co.touchlab:stately-concurrency:${Version.stately}"
    }

}

object Projects {
    const val core = ""
}
