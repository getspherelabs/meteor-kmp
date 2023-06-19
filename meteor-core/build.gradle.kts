import java.net.URL

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
            dependsOn(commonMain)
            dependencies {
                implementation(Libs.Android.viewModel)
            }
        }
        val androidUnitTest by getting {
            dependsOn(commonTest)
            dependencies {
                implementation(Libs.Coroutine.testJvm)
            }
        }
        val iosX64Main by getting {
            dependsOn(commonMain)
        }
        val iosArm64Main by getting {
            dependsOn(commonMain)
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(commonMain)
        }
        val iosMain by creating {
            dependsOn(commonMain)

            dependencies {}
        }
        val iosX64Test by getting {
            dependsOn(commonTest)
        }
        val iosArm64Test by getting {
            dependsOn(commonTest)
        }
        val iosSimulatorArm64Test by getting {
            dependsOn(commonTest)
        }
        val iosTest by creating {
            dependsOn(commonTest)

            dependencies {}
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

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.S01, automaticRelease = true)
    signAllPublications()
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    dokkaSourceSets {
        configureEach {
            sourceLink {
                localDirectory.set(projectDir.resolve("src"))
                remoteUrl.set(URL("https://github.com/getspherelabs/meteor/tree/main/meteor-core/src"))
                remoteLineSuffix.set("#L")
            }
        }
    }
}
