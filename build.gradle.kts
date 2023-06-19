import com.diffplug.gradle.spotless.SpotlessExtension

buildscript {
    val compose_ui_version by extra("1.2.0")

    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:${Version.ktlint}")
    }
}
plugins {
    id("com.android.library").version("7.4.2").apply(false)
    kotlin("multiplatform").version("1.8.10").apply(false)
    id("org.jlleitschuh.gradle.ktlint") version Version.ktlint
    id("org.jetbrains.kotlin.jvm") version "1.8.0" apply false
    id("com.android.application") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
    id("com.diffplug.spotless") version "6.17.0"
    id("com.vanniktech.maven.publish") version "0.25.2"
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "com.diffplug.spotless")

    configure<SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("**/build/**/*.kt")
            ktlint().setEditorConfigPath(
                "${project.rootDir}/.editorconfig"
            )
            trimTrailingWhitespace()
            endWithNewline()
        }
        kotlinGradle {
            target("*.gradle.kts")
            ktlint().setEditorConfigPath(
                "${project.rootDir}/.editorconfig"
            )
            trimTrailingWhitespace()
            endWithNewline()
        }
        format("xml") {
            target("**/*.xml")
        }
    }
    ktlint {
        debug.set(false)
        verbose.set(true)
        version.set("0.37.2")
        enableExperimentalRules.set(true)
        outputToConsole.set(true)
        ignoreFailures.set(false)
        enableExperimentalRules.set(false)
        additionalEditorconfigFile.set(file("$rootDir/.editorconfig"))
        filter {
            exclude { it.file.path.contains("build/") }
        }
    }
}
