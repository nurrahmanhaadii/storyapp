// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
    id("com.android.library") version "8.1.4" apply false
    id("com.android.dynamic-feature") version "8.1.4" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
}