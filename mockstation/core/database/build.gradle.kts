plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.sqldelight)
}

kotlin {
    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:model"))
            implementation(project(":core:util"))

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.bundles.sqldelight)
            implementation(libs.bundles.koinCommon)
        }

        val desktopMain by getting {
            dependencies {
                implementation(libs.sqldelight.driver.sqlite)
            }
        }

        commonTest.dependencies {
            implementation(libs.bundles.testingCommon)
        }
    }
}

sqldelight {
    databases {
        create("MockstationDatabase") {
            packageName.set("com.github.syunpeii.mockstation.core.database")
        }
    }
}
