plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.kotlinxSerialization)
        }

        commonTest.dependencies {
            implementation(libs.bundles.testingCommon)
        }
    }
}
