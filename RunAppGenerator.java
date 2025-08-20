import tools.generator.NewSpringAppGenerator;

/**
 * RunAppGenerator is a simple wrapper to invoke the NewSpringAppGenerator
 * from your Gradle project.
 *
 * Usage:
 * 1. Non-interactive mode (pass app name and package as arguments):
 * ./gradlew run -PmainClass=RunAppGenerator --args="RumpusApp com.rumpus"
 *
 * This will generate:
 * - src/main/java/com/rumpus/RumpusApp.java
 * - src/main/resources/application-rumpusapp.properties
 * - standard subfolders: controllers, services, repositories, entities
 *
 * 2. Interactive mode (no arguments):
 * ./gradlew run -PmainClass=RunAppGenerator
 *
 * You will be prompted to enter:
 * - App name (e.g., ChuckApp)
 * - Package name (e.g., com.chuck)
 *
 * Notes:
 * - Place this file in your client project (e.g., Rumpus repo) that has
 * access to the common backend tools repo.
 * - The generator creates skeleton files only; you may want to add templates
 * for controllers, services, and entities in the generator for a more complete
 * setup.
 * - TODO: not currently working as of 2025/09/19
 */
public class RunAppGenerator {

    public static void main(String[] args) {
        // Simply delegate to the NewSpringAppGenerator
        NewSpringAppGenerator.main(args);
    }
}
