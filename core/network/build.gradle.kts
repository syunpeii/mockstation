plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:model"))
            implementation(project(":core:util"))

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.bundles.ktorClient)
            implementation(libs.bundles.koinCommon)
        }

        val desktopMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }

        commonTest.dependencies {
            implementation(libs.bundles.testingCommon)
        }
    }
}
