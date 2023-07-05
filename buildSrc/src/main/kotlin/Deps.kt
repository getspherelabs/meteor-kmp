object Version {
    const val coroutine = "1.7.1"
    const val viewModel = "2.2.0"
    const val ktlint = "10.3.0"
    const val coroutineTest = "1.7.1"
    const val dokka = "1.8.20"
    const val lifecycle = "2.6.0"
    const val stately = "2.0.0"
    const val datetime = "0.4.0"
    const val js = "1.6.21"
}

object Libs {
    object Coroutine {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutine}"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutineTest}"
        const val testJvm = "org.jetbrains.kotlinx:kotlinx-coroutines-test-jvm:${Version.coroutineTest}"
        const val turbine = "app.cash.turbine:turbine:0.12.3"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"
    }

    object Android {
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycle}"
    }

    object Ios {
        const val stately = "co.touchlab:stately-concurrency:${Version.stately}"
    }

    object Hilt {

    }

    object Kotlin {
        const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:${Version.datetime}"
        const val js = "org.jetbrains.kotlin:kotlin-stdlib-js:${Version.js}"
    }

}

object Projects {
    const val core = ""
}
