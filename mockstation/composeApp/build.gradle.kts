import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting {
            dependencies {
                implementation(project(":core:data"))
                implementation(project(":core:database"))
                implementation(project(":core:datastore"))
                implementation(project(":core:designsystem"))
                implementation(project(":core:domain"))
                implementation(project(":core:model"))
                implementation(project(":core:network"))
                implementation(project(":core:util"))

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(compose.desktop.currentOs)
                implementation(compose.uiTooling)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.coroutines.swing)
                implementation(libs.kotlinx.datetime)
                implementation(libs.bundles.koinCommon)
                implementation(libs.koin.compose)
                implementation(libs.bundles.navigation)
            }
        }

        val desktopTest by getting {
            dependencies {
                implementation(libs.bundles.testingCommon)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.github.syunpeii.mockstation.app.MainDesktopKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.github.syunpeii.mockstation"
            packageVersion = "1.0.0"
        }
    }
}
