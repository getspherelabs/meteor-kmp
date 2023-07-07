plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.13.2"
    id("com.vanniktech.maven.publish") version "0.25.2"
}

kotlin {
    explicitApi()
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
        publishAllLibraryVariants()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "meteor-viewmodel"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
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
            dependsOn(commonMain)

            dependencies {
                with(Libs.Android) {
                    implementation(viewModel)
                }
            }
        }
        val androidUnitTest by getting {
            dependsOn(commonTest)

            dependencies {
                implementation(Libs.Coroutine.test)
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

    namespace = "io.spherelabs.meteorviewmodel"
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

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.S01, automaticRelease = true)
    signAllPublications()
}
