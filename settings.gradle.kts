pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }
}

rootProject.name = "meteor"
include(":meteor-core")
include(":meteor-test")
include(":samples")
include(":samples:counter-kmm")
include(":samples:counter-kmm:androidapp")
include(":meteor-viewmodel")
include(":samples:counter-compose")
