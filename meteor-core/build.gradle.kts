plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.13.2"
    id("com.vanniktech.maven.publish") version "0.25.2"
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "meteor-core"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":meteor-viewmodel"))
                api(Libs.Coroutine.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(Libs.Coroutine.test)
                implementation(Libs.Coroutine.turbine)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Libs.Android.viewModel)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(Libs.Coroutine.testJvm)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    namespace = "io.spherelabs.meteor"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
    // still needed for Android projects despite toolchain
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(1.8)
        targetCompatibility = JavaVersion.toVersion(1.8)
    }
}
