plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ktor)
    application
}

group = "com.mockstation"
version = "1.0.0"

application {
    mainClass.set("com.github.syunpeii.mockstation.server.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:util"))

    implementation(libs.bundles.ktorServer)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.bundles.koinCommon)
    implementation(libs.koin.ktor)
    implementation(libs.logback)

    testImplementation(libs.bundles.testingJvm)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(project(":core:testing"))
}
