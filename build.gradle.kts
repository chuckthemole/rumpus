import org.springframework.boot.gradle.tasks.run.BootRun
import com.rumpushub.buildlogic.utils.EnvLoader
import com.rumpushub.buildlogic.plugins.RumpusPlugin
import com.rumpushub.buildlogic.plugins.AwsDependenciesPlugin
import com.rumpushub.buildlogic.plugins.CommonSessionDependencies
import com.rumpushub.buildlogic.plugins.RumpusTest
import com.rumpushub.buildlogic.plugins.RumpusTestConventions
import com.rumpushub.buildlogic.plugins.RumpusDependenciesPlugin
import com.rumpushub.buildlogic.plugins.OpenApiDependenciesPlugin

// --------------------------------------------------------------------------
// Apply custom Rumpus-specific Gradle plugins
// --------------------------------------------------------------------------
apply<RumpusPlugin>()

apply<AwsDependenciesPlugin>()
configure<AwsDependenciesPlugin.AwsExtension> {
    awsCoreDependency = rumpusLibs.springCloudAws
    awsS3Dependency = rumpusLibs.springCloudAwsS3
}

apply<CommonSessionDependencies>()
configure<CommonSessionDependencies.SessionExtension> {
    core = rumpusLibs.springSessionCore
    jdbc = rumpusLibs.springSessionJdbc
}

apply<RumpusTest>()
configure<RumpusTest.TestExtension> {
    springBoot = rumpusLibs.springBootStarterTest
    mockito = rumpusLibs.mockito
    junitApi = rumpusLibs.junit
    junitEngine = rumpusLibs.junitEngine
    springSecurityTest = rumpusLibs.springSecurityTest
}

apply<RumpusTestConventions>()
configure<RumpusTestConventions.TestConventionsExtension> {
    junitVersion = rumpusLibs.junit4
    showStandardStreams = true
}

apply<OpenApiDependenciesPlugin>()
configure<OpenApiDependenciesPlugin.OpenApiExtension> {
    springdocCore = rumpusLibs.openApiUi
}

apply<RumpusDependenciesPlugin>()

configure<RumpusDependenciesPlugin.RumpusDepsExtension> {
    core.addAll(listOf(
        rumpusLibs.rumpusSpringBoot.get(),
        rumpusLibs.springBootWeb.get()
    ))

    web.addAll(listOf(
        rumpusLibs.webFlux.get(),
        rumpusLibs.webSocket.get()
    ))

    db.addAll(listOf(
        rumpusLibs.jpa.get(),
        rumpusLibs.jdbc.get(),
        rumpusLibs.mysql.get()
    ))

    security.addAll(listOf(
        rumpusLibs.springSecurity.get(),
        rumpusLibs.oauth2Client.get(),
        rumpusLibs.jjwtApi.get(),
        rumpusLibs.jjwtImpl.get(),
        rumpusLibs.jjwtJackson.get()
    ))

    cloud.addAll(listOf(
        rumpusLibs.springCloudAws.get(),
        rumpusLibs.springCloudAwsS3.get()
    ))

    devTools.addAll(listOf(
        rumpusLibs.devTools.get()
    ))

    testing.addAll(listOf(
        rumpusLibs.junit.get(),
        rumpusLibs.mockito.get()
    ))

    // Miscellaneous dependencies that don’t neatly fit in other buckets
    additionalDeps.addAll(listOf(
        rumpusLibs.springBootActuator.get(),
        rumpusLibs.springBootAdminClient.get(),
        rumpusLibs.springBootAdminServer.get(),
        rumpusLibs.commonsValidator.get(),
        rumpusLibs.bootstrap.get(),
        rumpusLibs.htmlunit.get(),
        rumpusLibs.unirest.get(),
        rumpusLibs.jsr305.get(),
        rumpusLibs.j2html.get(),
        rumpusLibs.jython.get(),
        rumpusLibs.tess4j.get(),
        rumpusLibs.oauth2ResourceServer.get()
    ))
}

/*
 * --------------------------------------------------------------------------
 * Project Metadata
 * --------------------------------------------------------------------------
 * - group:    Logical namespace for artifacts published from this project.
 * - version:  Project version identifier.
 *
 * Both values are centralized in `gradle/rumpusLibs.versions.toml`.
 * Accessed via the generated type-safe accessor `rumpusLibs.versions`.
 * This avoids hardcoding metadata across multiple modules.
 */
group = rumpusLibs.versions.rumpusGroup.get()
version = rumpusLibs.versions.rumpus.get()

/*
 * --------------------------------------------------------------------------
 * Plugins
 * --------------------------------------------------------------------------
 * - 'rumpus':      Custom plugin providing common Rumpus build logic
 * - 'aws':         Custom plugin for AWS dependencies/configuration
 * - 'springBoot':  Official Spring Boot Gradle plugin (3.x)
 * - 'dependencyManagement': Spring's dependency-management plugin for centralized dependency control
 *
 * Both external plugins are declared via the version catalog (rumpusLibs.versions.toml).
 */
plugins {
    alias(rumpusLibs.plugins.springBoot)
    alias(rumpusLibs.plugins.dependencyManagement)
}

/*
 * --------------------------------------------------------------------------
 * Environment Setup
 * --------------------------------------------------------------------------
 * EnvLoader:
 *   - Reads `.env` files from project root (or parent directory).
 *   - Injects values into Gradle’s `extraProperties`, making them accessible via `project.findProperty`.
 *
 * Runtime environment:
 *   - ENV:   Controls dependency resolution and repository usage (DEV, BETA, LIVE).
 *   - HEAP:  Controls JVM heap size profile (e.g., LIMITED_HEAP).
 */
EnvLoader.loadDotEnv(project)

val env: String = project.findProperty("ENV") as? String ?: "DEV"
println("Rumpus is using environment: $env")

val heap: String = project.findProperty("HEAP") as? String ?: "LIMITED_HEAP"
println("Rumpus is using heap configuration: $heap")

/*
 * --------------------------------------------------------------------------
 * Repositories
 * --------------------------------------------------------------------------
 * - gradlePluginPortal + Maven Central (always available).
 * - LIVE: Pulls artifacts from GitHub Packages (requires GPR_USER + GPR_TOKEN).
 * - BETA: Uses a local test repository ($rootDir/TestRepo).
 * - DEV: Uses Maven Local to resolve locally published artifacts.
 */
repositories {
    gradlePluginPortal()
    mavenCentral()

    when (env) {
        "LIVE" -> maven {
            url = uri("https://maven.pkg.github.com/chuckthemole/common")
            credentials {
                username = project.findProperty("GPR_USER") as? String ?: System.getenv("GPR_USER")
                password = project.findProperty("GPR_TOKEN") as? String ?: System.getenv("GPR_TOKEN")
            }
        }
        "BETA" -> maven { url = uri("$rootDir/TestRepo") }
        "DEV" -> mavenLocal()
    }
}

/*
 * --------------------------------------------------------------------------
 * Dependencies
 * --------------------------------------------------------------------------
 * DEV:
 *   - Uses local project dependency (:common) for live development and IDE navigation.
 *   - Keeps iteration fast by avoiding remote lookups.
 *
 * BETA/LIVE:
 *   - Resolves published artifact com.rumpushub.common:common via version catalog.
 *   - Versions are centrally managed in `gradle/rumpusLibs.versions.toml`.
 */
dependencies {
    when (env) {
        "DEV" -> {
            println("Rumpus is loading dependency :common (local project)")
            add("implementation", project(":common"))

            // Optional: enforce build ordering between :common and this project
            // tasks.named("compileJava") { dependsOn(":common:compileJava") }
            // tasks.named("processResources") { dependsOn(":common:processResources") }
        }
        else -> { // BETA or LIVE
            println("Rumpus is loading dependency com.rumpushub.common:common (from version catalog)")
            add("implementation", rumpusLibs.common)
        }
    }
}

/*
 * --------------------------------------------------------------------------
 * Spring Boot Run Configuration
 * --------------------------------------------------------------------------
 * Configures `bootRun` task:
 *   - Passes `env` as a system property to the running Spring Boot app.
 *   - Applies JVM memory settings based on the selected heap profile.
 */
tasks.named<BootRun>("bootRun") {
    println("bootRun: setting environment properties...")
    systemProperty("env", if (env == "DEV") "dev" else "live")

    println("bootRun: checking memory profile...")
    if (heap == "LIMITED_HEAP") {
        println("Running in LIMITED_HEAP mode")
        jvmArgs = listOf("-Xmx512m", "-Xms256m")
    }
}

/*
 * --------------------------------------------------------------------------
 * Notes / TODOs
 * --------------------------------------------------------------------------
 * - Frontend tasks (NodeJS/webpack) were intentionally removed for clarity.
 *   Reintroduce them if frontend build integration is needed.
 * - Test task wiring for `:common` is currently commented out; revisit if 
 *   cross-project test execution is required.
 * - EnvLoader provides Kotlin DSL–friendly environment loading.
 * - Dependency versions are now centralized via `rumpusLibs.versions.toml` 
 *   (avoid hardcoding versions in build scripts).
 */
