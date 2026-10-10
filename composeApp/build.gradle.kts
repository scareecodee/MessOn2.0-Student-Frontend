import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
group = "com.messon.student"
plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")

    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jetbrains.kotlin.plugin.compose")

    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    id("com.google.gms.google-services")
}


kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    swiftPMDependencies {
        swiftPackage(
            url = url("https://github.com/firebase/firebase-ios-sdk.git"),
            version = from("12.5.0"),
            products = listOf(
                product("FirebaseMessaging"),
                product("FirebaseCore")
            )
        )
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)

            implementation(libs.koin.compose)

            implementation(libs.ktor.client.okhttp)
            implementation(libs.ktor.client.android)


            implementation(libs.androidx.credentials)
            implementation(libs.androidx.credentials.play.services.auth)
            implementation(libs.googleid)
            implementation(libs.play.services.auth)

            implementation("androidx.security:security-crypto:1.1.0-alpha06")
            implementation(libs.firebase.messaging)


        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.preview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.encoding)

            implementation("io.coil-kt.coil3:coil-compose:3.3.0")

            implementation("tech.annexflow.compose:constraintlayout-compose-multiplatform:0.6.1")

            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.navigation.compose)

            implementation(compose.materialIconsExtended)

            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")

            implementation("io.github.alexzhirkevich:qrose:1.1.1")

            implementation("co.touchlab:kermit:2.0.8")

            //toast
            implementation("network.chaintech:cmptoast:1.0.71")
            implementation("network.chaintech:qr-kit:3.1.3")


            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)

            implementation("org.jetbrains.compose.material3:material3:1.9.0")

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)

        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)


        }
    }
}

android {
    namespace = "com.messon.student.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.messon.student.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}


room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    ksp(libs.room.compiler)
}