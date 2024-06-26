plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.google.gms.google.services)
    kotlin("kapt")
    id ("com.google.dagger.hilt.android")

}

android {
    namespace = "uk.ac.tees.mad.d3722574"
    compileSdk = 34

    defaultConfig {
        applicationId = "uk.ac.tees.mad.d3722574"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    //lottie
    implementation(libs.lottie.compose)
    //navigation
    implementation(libs.androidx.navigation.compose)

    //Icons
    implementation(libs.androidx.material.icons.extended)

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    //Authentication
    implementation(libs.firebase.auth)

    implementation (libs.firebase.firestore)
    implementation (libs.firebase.storage)

    implementation(libs.play.services.auth)

// ViewModel
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
// ViewModel utilities for Compose
    implementation (libs.androidx.lifecycle.viewmodel.compose)

    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)
    implementation (libs.androidx.hilt.navigation.compose)

    implementation(libs.coil.compose)

    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.maps.ktx)
    implementation(libs.maps.compose)

    implementation (libs.retrofit)
    implementation (libs.converter.gson)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}