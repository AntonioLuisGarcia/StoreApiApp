// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("androidx.navigation.safeargs.kotlin:androidx.navigation.safeargs.kotlin.gradle.plugin:2.7.5")
    }
}

plugins {
    id("androidx.navigation.safeargs.kotlin") version "2.7.1" apply false
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("org.jetbrains.kotlin.kapt") version "2.0.0-Beta1" apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
}