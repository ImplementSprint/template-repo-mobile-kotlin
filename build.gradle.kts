import com.github.jk1.license.filter.LicenseBundleNormalizer
import org.gradle.language.base.plugins.LifecycleBasePlugin

plugins {
    base
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("org.jlleitschuh.gradle.ktlint") version "14.2.0" apply false
    id("org.owasp.dependencycheck") version "12.2.0"
    id("com.github.jk1.dependency-license-report") version "3.1.1"
}

licenseReport {
    filters = arrayOf(LicenseBundleNormalizer())
    allowedLicensesFile = layout.projectDirectory.file("config/allowed-licenses.json").asFile
}

dependencyCheck {
    // Do not fail governance on upstream NVD feed/update tool crashes.
    // The report still gets generated when data is available.
    failOnError = false

    nvd {
        apiKey = System.getenv("NVD_API_KEY")
    }
}

tasks.register("unitTest") {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Runs Kotlin unit tests."
    dependsOn(":app:testDebugUnitTest")
}

tasks.register("lintAndStyleCheck") {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Runs Android lint and Kotlin style checks."
    dependsOn(":app:lintDebug", ":app:ktlintCheck")
}

tasks.register("dependencyAudit") {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Runs dependency vulnerability audit."
    dependsOn("dependencyCheckAnalyze")
}

tasks.register("licenseCompliance") {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Checks third-party dependencies against the allowed license policy."
    dependsOn("checkLicense")
}

tasks.named("check") {
    dependsOn("unitTest", "lintAndStyleCheck", "dependencyAudit", "licenseCompliance")
}
