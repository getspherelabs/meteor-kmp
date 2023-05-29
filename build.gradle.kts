buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:10.3.0")
    }
}
plugins {
    id("com.android.library").version("8.0.1").apply(false)
    kotlin("multiplatform").version("1.8.10").apply(false)
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

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

    afterEvaluate {
        tasks.named("check") {
            dependsOn(tasks.getByName("ktlintCheck"))
        }
    }
}


