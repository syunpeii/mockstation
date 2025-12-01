plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:model"))
            implementation(project(":core:util"))

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.bundles.ktorClient)
            implementation(libs.bundles.koinCommon)
        }

        jvmMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        commonTest.dependencies {
            implementation(libs.bundles.testingCommon)
        }
    }
}
