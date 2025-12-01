plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:database"))
            implementation(project(":core:datastore"))
            implementation(project(":core:model"))
            implementation(project(":core:network"))
            implementation(project(":core:util"))

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.bundles.koinCommon)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
