plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:util"))

            implementation(libs.androidx.datastore.preferences)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.bundles.koinCommon)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
