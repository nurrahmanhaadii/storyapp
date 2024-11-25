plugins {
    id("com.android.dynamic-feature")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")

}
android {
    namespace = "id.haadii.dicoding.submission.favorite"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    }
}

dependencies {
    implementation(project(":app"))
    implementation(project(":core"))
    implementation(project(":domain"))

    implementation("androidx.core:core-ktx:1.9.0")
//    implementation("androidx.appcompat:appcompat:1.7.0")
//    implementation("com.google.android.material:material:1.12.0")
//    implementation("androidx.activity:activity:1.9.3")
//    implementation("androidx.constraintlayout:constraintlayout:2.2.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation("androidx.activity:activity-ktx:1.6.0")

    // GLIDE IMAGE LOADER
    implementation("com.github.bumptech.glide:glide:4.14.2")
    kapt("com.github.bumptech.glide:glide:4.14.2")

}

kapt {
    correctErrorTypes = true
}