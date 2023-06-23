object Version {
    const val kotlin = "1.8.10"
    const val coil = "1.4.0"
    const val koinCore = "3.3.0"
    const val koinAndroid = "3.3.1"
    const val ktor = "2.2.1"
    const val sqlDelight = "1.5.5"
    const val json = "1.5.0"
    const val coroutines = "1.6.4"
    const val serializationCore = "1.4.1"
    const val meteor = "0.1.2"
    const val navigation = "2.5.1"
    const val turbine = "1.0.0"
}

object Deps {
    object Compose {
        const val navigation = "androidx.navigation:navigation-compose:${Version.navigation}"
        const val coil = "io.coil-kt:coil-compose:${Version.coil}"
    }

    object Kotlin {
        const val serialization = "org.jetbrains.kotlin:kotlin-serialization:${Version.kotlin}"
        const val jsonSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Version.json}"
        const val serializationCore = "org.jetbrains.kotlinx:kotlinx-serialization-core:${Version.serializationCore}"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"
    }

    object Koin {
        const val android = "io.insert-koin:koin-android:${Version.koinAndroid}"
        const val core = "io.insert-koin:koin-core:${Version.koinCore}"
        const val compose = "io.insert-koin:koin-androidx-compose:3.3.0"
    }

    object Ktor {
        const val core = "io.ktor:ktor-client-core:${Version.ktor}"
        const val content = "io.ktor:ktor-client-content-negotiation:${Version.ktor}"
        const val serializationJson = "io.ktor:ktor-serialization-kotlinx-json:${Version.ktor}"
        const val logging = "io.ktor:ktor-client-logging:${Version.ktor}"
        const val darwin = "io.ktor:ktor-client-darwin:${Version.ktor}"
        const val okhttp = "io.ktor:ktor-client-okhttp:${Version.ktor}"
    }

    object SqlDelight {
        const val runtime = "com.squareup.sqldelight:runtime:${Version.sqlDelight}"
        const val extension = "com.squareup.sqldelight:coroutines-extensions:${Version.sqlDelight}"
        const val native = "com.squareup.sqldelight:native-driver:${Version.sqlDelight}"
        const val android = "com.squareup.sqldelight:android-driver:${Version.sqlDelight}"
    }

    object Meteor {
        const val core = "io.github.behzodhalil:meteor-mvi:${Version.meteor}"
        const val viewmodel = "io.github.behzodhalil:meteor-viewmodel:${Version.meteor}"
    }

    object Testing {
        const val turbine = "app.cash.turbine:turbine:${Version.turbine}"
        const val koin = "io.insert-koin:koin-test:${Version.koinCore}"
    }
}
