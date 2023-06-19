import java.net.URL

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.vanniktech.maven.publish") version "0.25.2"
    id("org.jetbrains.dokka") version Version.dokka
}

kotlin {
    explicitApi()
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
            baseName = "meteor-test"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":meteor-core"))
                implementation(Libs.Coroutine.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(project(":meteor-core"))
                implementation(Libs.Coroutine.test)
            }
        }
        val androidMain by getting
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {}
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {}
    }
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    namespace = "io.spherelabs.meteortest"
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
            if (name.startsWith("ios")) {
                displayName.set("ios")
            }

            sourceLink {
                localDirectory.set(projectDir.resolve("src"))
                remoteUrl.set(URL("https://github.com/getspherelabs/meteor/tree/main/meteor-test/src"))
                remoteLineSuffix.set("#L")
            }
        }
    }
}
