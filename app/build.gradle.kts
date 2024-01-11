plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.achsanit.movieapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.achsanit.movieapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // create build config for api key, base api url, and base image url TMBD
        val tmdbApiKey: String by project
        buildConfigField("String","BASE_TMDB_KEY", tmdbApiKey)

        val baseApiUrl: String by project
        buildConfigField("String", "BASE_TMDB_API", baseApiUrl)

        val baseImageUrl: String by project
        buildConfigField("String", "BASE_IMAGE_URL", baseImageUrl)

        val apiVersion: String by project
        buildConfigField("String", "API_VERSION", apiVersion)
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
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.google.android.flexbox:flexbox:3.0.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    val nav_version = "2.7.5" // navigation component
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    val lifecycle_version = "2.6.2" // ViewModel LiveData (lifecycle)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    val retrofit_version = "2.9.0" // retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    val chucker_version = "3.5.2" // chucker for dev logging
    implementation("com.github.chuckerteam.chucker:library:$chucker_version")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:$chucker_version")

    val koin_version = "3.5.0" // koin for DI
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-android:$koin_version")

    val room_version = "2.6.0" // room
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-rxjava2:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    val coil_version = "1.3.2" // coil for load image
    implementation("io.coil-kt:coil:$coil_version")

    val shimmer = "0.5.0" // view for loading event
    implementation("com.facebook.shimmer:shimmer:$shimmer")

    val lottie = "5.0.3" // library for animation
    implementation("com.airbnb.android:lottie:$lottie")
}