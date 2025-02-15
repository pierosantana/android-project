plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") version "4.4.2"
}

android {
    namespace = "es.upgrade"
    compileSdk = 35

    defaultConfig {
        applicationId = "es.upgrade"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //Lottie Animations
    implementation("com.airbnb.android:lottie:6.6.2")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.8.0"))


    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-database")

    //Dependecies UI
    implementation ("androidx.viewpager2:viewpager2:1.1.0")
    implementation ("com.tbuonomo:dotsindicator:5.1.0")
    implementation ("com.google.android.material:material:1.12.0")

    //Denpedencies for API
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.converterGson)

    //Dependencias para la imagen de usuario
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("androidx.appcompat:appcompat:1.5.1")

    //Dependencias para coger la imagen de la API (Glide)
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")

    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("androidx.recyclerview:recyclerview:1.2.1")



    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries

}