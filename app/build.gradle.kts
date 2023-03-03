@file:Suppress("UnstableApiUsage")

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    id("realm-android")
}

android {
    defaultConfig {
        applicationId = "ru.myitschool.lab23"
        versionCode = 1
        versionName = "0.0.1"

        targetSdk = 33
        minSdk = 27
        compileSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    lint {
        warningsAsErrors = true
        ignoreWarnings = false
        abortOnError = true
        checkAllWarnings = true
        lintConfig = file("lint.xml")
        lint {
            disable.addAll(
                listOf(
                    "InvalidPackage",
                    "UnusedIds",
                    "GradleDependency",
                    "UnusedResources",
                    "SyntheticAccessor",
                ),
            )
        }
    }

    applicationVariants.all {
        val lintTask = tasks["lint${name.capitalize()}"]
        assembleProvider.get().dependsOn.add(lintTask)
    }

    buildFeatures {
        viewBinding = true
    }
    namespace = "ru.myitschool.lab23"
}

dependencies {

    implementation(libs.android.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.androidx.constraintlayout)

    implementation("com.instabug.library:instabug:11.8.0")

    // only this version does not generate gradle merge problems
    androidTestImplementation(libs.androidx.test.espresso.accessibility)
    androidTestImplementation(libs.kotlinx.coroutines.test)

    implementation("io.realm.kotlin:library-base:1.5.0")
    implementation("io.realm:android-adapters:4.0.0")

    // Coroutines
    // implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7'
    // implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9'

    // Coroutine Lifecycle Scopes
    // implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
    // implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.2.0"

    androidTestImplementation(libs.kakao)
    androidTestImplementation(libs.androidx.test.uiautomator)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(kotlin("test"))
    testImplementation(libs.androidx.test.ext)
}
