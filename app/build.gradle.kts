plugins {
    // Plugin principal para crear la aplicacion Android
    id("com.android.application")

    // Plugin para usar Compose con Kotlin
    id("org.jetbrains.kotlin.plugin.compose")

    // KSP genera el codigo que necesita Room
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.prestaaula"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.prestaaula"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2026.09.00"))
    // Componentes principales de Android y Jetpack Compose
    implementation("androidx.activity:activity-compose:1.13.0")
    // Animaciones que se usan en los cambios entre pantallas
    implementation("androidx.compose.animation:animation")
    implementation("androidx.compose.material3:material3")
    // Iconos de Material para botones y tarjetas
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.core:core-ktx:1.19.1")
    // Navegacion entre las pantallas de PrestaAula
    implementation("androidx.navigation:navigation-compose:2.10.2")
    // Room para guardar equipos y reservas en el dispositivo
    implementation("androidx.room:room-runtime:2.8.5")
    implementation("androidx.room:room-ktx:2.8.5")
    ksp("androidx.room:room-compiler:2.8.5")
    // ViewModel y ciclo de vida para observar los estados desde Compose
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.11.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.11.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.11.0")

    // DataStore guarda la preferencia del tema claro u oscuro
    implementation("androidx.datastore:datastore-preferences:1.3.0")

    // Dependencias para pruebas
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation(platform("androidx.compose:compose-bom:2026.02.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")

    // Herramientas que se usan durante desarrollo y pruebas
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    debugImplementation("androidx.compose.ui:ui-tooling")
}
