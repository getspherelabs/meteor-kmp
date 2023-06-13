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
    id("org.jetbrains.dokka") version Version.dokka
    id("org.jetbrains.kotlin.jvm") version "1.8.0" apply false
    id("com.android.application") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "org.jetbrains.dokka")

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

    val dokkaOutputDir = "$buildDir/dokka"

    tasks.dokkaHtml {
        outputDirectory.set(file(dokkaOutputDir))
    }

    val deleteDokkaOutputDir by tasks.register<Delete>("deleteDokkaOutputDirectory") {
        delete(dokkaOutputDir)
    }

    val javadocJar = tasks.register<Jar>("javadocJar") {
        dependsOn(deleteDokkaOutputDir, tasks.dokkaHtml)
        archiveClassifier.set("javadoc")
        from(dokkaOutputDir)
    }

    tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
        dokkaSourceSets {
            moduleName.set("Meteor")
        }
    }

}


