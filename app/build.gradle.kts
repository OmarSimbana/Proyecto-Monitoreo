plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.monitoreo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.monitoreo"
        minSdk = 24
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
}

dependencies {
<<<<<<< HEAD
    // Dependencias básicas de AndroidX
=======
>>>>>>> 73a1ac4e2a2c0491ddf61732de0b301912e88ee5
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
<<<<<<< HEAD

    // Dependencias para GPS y ubicación
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Servidor HTTP embebido (actualizado o alternativa)
    implementation("org.nanohttpd:nanohttpd:2.3.1") // Revisa si hay versión más reciente

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
=======
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("org.nanohttpd:nanohttpd:2.3.1")
}
>>>>>>> 73a1ac4e2a2c0491ddf61732de0b301912e88ee5
