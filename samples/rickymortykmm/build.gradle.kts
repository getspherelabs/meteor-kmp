buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
    }
}
plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("7.3.1").apply(false)
    id("com.android.library").version("7.3.1").apply(false)
    kotlin("android").version("1.8.10").apply(false)
    kotlin("multiplatform").version("1.8.10").apply(false)
    kotlin("jvm").version("1.8.10")
    kotlin("plugin.serialization").version("1.8.10")
}
