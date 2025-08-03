plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.serialization") version "2.2.0"
    alias(libs.plugins.hilt.android)
    id("com.google.devtools.ksp")

    alias(libs.plugins.compose.compiler)

}

android {
    namespace = "ru.mrapple100.rickmorty"
    compileSdk = 35


    defaultConfig {
        applicationId = "ru.mrapple100.rickmorty"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":data"))


    // DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation (libs.androidx.hilt.navigation.compose)



    implementation(libs.kotlinx.serialization.json)



    // Compose
    implementation (libs.androidx.ui)
    implementation (libs.androidx.material)
    implementation (libs.androidx.ui.tooling.preview)
    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation (libs.androidx.activity.compose)

    // Orbit
    implementation (libs.orbit.core)
    implementation (libs.orbit.viewmodel)
    implementation (libs.orbit.compose)
    testImplementation (libs.orbit.test)

    // Coil
    implementation (libs.coil.compose)

    implementation (libs.androidx.palette.ktx)

    //animation
    implementation(libs.androidx.compose.animation)

    // Navigation Compose
    implementation (libs.androidx.navigation)
    implementation(libs.accompanist.navigation.animation)

    //Shimmer
    implementation(libs.compose.shimmer)



    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)



}