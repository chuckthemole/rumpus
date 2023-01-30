import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpx

class GreetingPlugin implements Plugin<Project> {
   void apply(Project project) {
      project.task('hello') << {
         println "Hello from the GreetingPlugin"
      }
   }
}