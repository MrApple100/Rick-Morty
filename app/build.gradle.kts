plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.serialization") version "2.2.0"
    alias(libs.plugins.hilt.android)
    id("com.google.devtools.ksp")
    id ("com.google.gms.google-services")
    alias(libs.plugins.compose.compiler)

}

android {
    namespace = "ru.mrapple100.rickmorty"
    compileSdk = 35


    defaultConfig {
        applicationId = "ru.mrapple100.rickmorty"
        minSdk = 30
        targetSdk = 35
        versionCode = 2
        versionName = "2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
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

    implementation(libs.androidx.multidex)


    // DI
    implementation(libs.hilt.android)
    implementation(libs.androidx.constraintlayout.compose.android)
    ksp(libs.hilt.android.compiler)
    implementation (libs.androidx.hilt.navigation.compose)



    implementation(libs.kotlinx.serialization.json)





    // Compose
    implementation (libs.androidx.ui)
    implementation (libs.androidx.material)
    implementation (libs.androidx.ui.tooling.preview)
    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation (libs.androidx.activity.compose)
    implementation (libs.accompanist.pager)

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
    implementation (libs.lottie.compose)


    // Navigation Compose
    implementation (libs.androidx.navigation)
    implementation(libs.accompanist.navigation.animation)

    //Shimmer
    implementation(libs.compose.shimmer)

    //Splash
    implementation(libs.androidx.core.splashscreen)

    //Firebase
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.analytics)

    implementation(libs.accompanist.permissions)

    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.turbine) // Для тестирования Flow

//    testImplementation(libs.androidx.espresso.core)
//
//    testImplementation(libs.android.devices.kaspresso)
//    testImplementation(libs.kaspresso.compose.support)
//    testImplementation(libs.androidx.orchestrator)
//    testImplementation(libs.kaspresso)
//    testImplementation(libs.composekakao)
// Дополнительные зависимости для тестирования
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v370)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Тестирование: Kaspresso с поддержкой Compose
    androidTestImplementation(libs.kaspresso.v153)
    androidTestImplementation(libs.kaspresso.compose.support.v153)

    androidTestImplementation(libs.hamcrest)

//    androidTestImplementation(libs.androidXTestRunner)
//    androidTestImplementation(libs.composeUiTestJunit)


}