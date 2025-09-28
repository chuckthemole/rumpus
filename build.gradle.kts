import org.springframework.boot.gradle.tasks.run.BootRun
import com.rumpushub.buildlogic.utils.EnvLoader

import com.rumpushub.buildlogic.plugins.RumpusPlugin
import com.rumpushub.buildlogic.plugins.AwsDependenciesPlugin


apply<RumpusPlugin>()
apply<AwsDependenciesPlugin>() // TODO: review common.gradle comment regarding AWS plugin

/*
 * --------------------------------------------------------------------------
 * Plugins
 * --------------------------------------------------------------------------
 * - 'rumpus': custom plugin for Rumpus-specific build logic
 * - 'common.aws': TODO: review common.gradle comment regarding AWS plugin
 * - 'org.springframework.boot': Spring Boot plugin; version 3.x disables some defaults (CSRF/CORS)
 * - 'io.spring.dependency-management': allows centralized dependency version management
 */
plugins {
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.1.0"
}

/*
 * --------------------------------------------------------------------------
 * Java compatibility
 * --------------------------------------------------------------------------
 */
// java.sourceCompatibility = JavaVersion.VERSION_17
tasks.withType<org.gradle.api.tasks.compile.JavaCompile> {
    sourceCompatibility = "17"
    targetCompatibility = "17"
}

/*
 * --------------------------------------------------------------------------
 * Load environment variables from .env
 * --------------------------------------------------------------------------
 * EnvLoader reads `.env` files from project root or parent directory.
 * Loaded properties are available as `project.extensions.extraProperties`.
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
 * Conditional repository setup depending on environment:
 * - DEV: Maven Local
 * - BETA: Local TestRepo
 * - LIVE: GitHub Packages
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
 * - DEV: include local ':common' project for IDE support and live development
 * - BETA/LIVE: include published artifact from Maven
 */
dependencies {
    when (env) {
        "DEV" -> {
            println("Rumpus is loading dependency :common (local project)")
            add("implementation", project(":common"))

            // Force build order to ensure :common is compiled first
            // tasks.named("compileJava") { dependsOn(":common:compileJava") }
            // tasks.named("processResources") { dependsOn(":common:processResources") }
        }
        else -> { // LIVE or BETA
            println("Rumpus is loading dependency com.rumpushub.common:common")
            // Using version catalog (gradle/libs.versions.toml) for dependency management.
            //    - The actual version number is defined in: gradle/libs.versions.toml under [versions]
            //    - The module coordinates are defined in: gradle/libs.versions.toml under [libraries]
            //    - Update the version there (not here!) when bumping `com.rumpushub.common:common`.
            //    - This keeps versions centralized and easier to manage across projects.
            add("implementation", rumpusLibs.common)
        }
    }
}

/*
 * --------------------------------------------------------------------------
 * Spring Boot run configuration
 * --------------------------------------------------------------------------
 * Sets system properties and JVM memory options based on environment and heap profile.
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
 * - Frontend tasks (NodeJS/webpack) were removed for clarity; reintegrate if needed
 * - Test tasks for ':common' were commented out; revisit later
 * - EnvLoader ensures environment variables are loaded inline and Kotlin DSL compatible
 * - Reference: https://stackoverflow.com/questions/45754005/include-tests-from-other-module
 */
