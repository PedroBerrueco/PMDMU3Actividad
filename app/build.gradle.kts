plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //safe args
    id ("androidx.navigation.safeargs")
    //Kotlin kapt para room
    id ("kotlin-kapt")
}

android {
    namespace = "com.pberrueco.pmdmu3actividad"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pberrueco.pmdmu3actividad"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    viewBinding{
        enable = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    //navegation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    //Corroutines
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    //DataStore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.media3:media3-database:1.2.0")
    //Room
    //runtimeOnly("androidx.room:room-runtime:2.6.1")
    val room_version = "2.6.1"

    implementation ("androidx.room:room-runtime:$room_version")
    kapt ("androidx.room:room-compiler:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}