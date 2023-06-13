pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "meteor"
include(":meteor-core")
include(":meteor-test")
include(":samples")
include(":samples:counter-kmm")
include(":samples:counter-kmm:androidapp")
include(":meteor-viewmodel")
