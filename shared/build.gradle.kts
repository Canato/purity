import com.canhub.purity.Versions

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.9.10"
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

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
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutineCore}")
                implementation("io.ktor:ktor-client-core:${Versions.ktorVersion}")
                implementation("io.ktor:ktor-client-content-negotiation:${Versions.ktorVersion}")
                implementation("io.ktor:ktor-serialization-kotlinx-json:${Versions.ktorVersion}")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:${Versions.ktorVersion}")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-darwin:${Versions.ktorVersion}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "com.canhub.purity"
    compileSdk = com.canhub.purity.Versions.compileSdk
    defaultConfig {
        minSdk = com.canhub.purity.Versions.minSdk
    }
}