plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.ktor) apply false
    alias(libs.plugins.sqldelight) apply false
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("0.47.1")
        android.set(false)
        verbose.set(true)
        outputToConsole.set(true)
        outputColorName.set("RED")
        ignoreFailures.set(false)

        disabledRules.set(
            setOf(
                "experimental:function-expression-body",
                "parameter-list-wrapping",
                "filename",
                "max-line-length",
            ),
        )

        filter {
            exclude("**/generated/**")
            exclude("**/build/**")
            exclude { it.file.path.contains("/build/") }
            exclude { it.file.path.contains("/generated/") }
        }
    }
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom(files("$rootDir/config/detekt.yml"))

    source.setFrom(
        files(
            "composeApp/src",
            "server/src",
            "core/data/src",
            "core/database/src",
            "core/datastore/src",
            "core/designsystem/src",
            "core/domain/src",
            "core/model/src",
            "core/network/src",
            "core/testing/src",
            "core/util/src",
        ),
    )
}

tasks.register("formatKotlin") {
    group = "formatting"
    description = "Auto-format Kotlin code across all modules"
    dependsOn(subprojects.map { it.tasks.named("ktlintFormat") })
}

tasks.register("lintKotlin") {
    group = "verification"
    description = "Check Kotlin code formatting across all modules"
    dependsOn(subprojects.map { it.tasks.named("ktlintCheck") })
}

tasks.register("detektAll") {
    group = "verification"
    description = "Run detekt static analysis across all modules"
    dependsOn("detekt")
}

tasks.register("checkCode") {
    group = "verification"
    description = "Run all code quality checks (ktlint + detekt)"
    dependsOn("lintKotlin", "detektAll")
}

listOf(":composeApp", ":server").forEach { modulePath ->
    project(modulePath) {
        tasks.configureEach {
            if (name == "run") {
                dependsOn(rootProject.tasks.named("formatKotlin"))
            }
        }
    }
}

// Make all subproject check tasks depend on ktlint (already configured by plugin) and add detekt to root verification
allprojects {
    tasks.configureEach {
        if (name == "check") {
            dependsOn(rootProject.tasks.named("detektAll"))
        }
    }
}
