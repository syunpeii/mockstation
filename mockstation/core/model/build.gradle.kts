plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.kotlinxSerialization)
            implementation(libs.kotlinx.datetime)
        }

        commonTest.dependencies {
            implementation(libs.bundles.testingCommon)
        }
    }
}
