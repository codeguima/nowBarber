plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    // Adicionar KSP
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}
android {
    namespace = "br.edu.up.nowbarber"
    compileSdk = 35  // Alterado para 35

    defaultConfig {
        applicationId = "br.edu.up.nowbarber"
        minSdk = 24
        targetSdk = 35  // Pode manter o targetSdk como 35 também
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
    buildToolsVersion = "35.0.0"
}


dependencies {
    // Dependências principais
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    // Dependências do Compose
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    // Material Design
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended.v150)
    implementation(libs.androidx.ui.android)
    implementation (libs.androidx.foundation)

    // Navegação
    implementation(libs.androidx.navigation.compose)

    //datastore

    implementation (libs.gson.v289)
    implementation (libs.androidx.datastore.preferences)
    implementation (libs.gson)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.analytics)
    implementation (libs.firebase.firestore)
    implementation (libs.firebase.auth)
    implementation (libs.firebase.storage)


    // Biblioteca de segurança (senha)
    implementation(libs.jbcrypt)

    // Room Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.androidx.appcompat)
    ksp(libs.androidx.room.compiler)


    // ViewModel e LiveData
    implementation(libs.androidx.lifecycle.viewmodel.ktx.v261)
    implementation(libs.androidx.lifecycle.livedata.ktx.v261)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate.v261)
    implementation (libs.androidx.lifecycle.runtime.compose)


    // Testes
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Depuração
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //google play services

    implementation (libs.play.services.auth)
    implementation (libs.play.services.location)

    //carregaR IMAGENS

    implementation(libs.coil.compose)
}

