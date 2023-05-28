buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
    }
}
plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.library").version("8.0.1").apply(false)
    kotlin("multiplatform").version("1.8.10").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
