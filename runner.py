import os
import sys

# Define paths
src_dir = 'src/main/java'
build_dir = 'build/classes/java/main'
tools_dir = 'tools'
common_dependencies = "$(gradle -q printClasspath)"

# Define base commands
build = ".././gradlew clean build --refresh-dependencies -x test > src/main/java/com/rumpus/rumpus/log/build.log"
buildXlint = ".././gradlew clean build --refresh-dependencies -x test -Xlint:unchecked > src/main/java/com/rumpus/rumpus/log/build.log"
buildDebug = ".././gradlew clean build --refresh-dependencies --debug -x test > src/main/java/com/rumpus/rumpus/log/build.log"
buildTest = ".././gradlew clean build --refresh-dependencies > src/main/java/com/rumpus/rumpus/log/build.log"
buildTestInfo = ".././gradlew clean build --info --refresh-dependencies > src/main/java/com/rumpus/rumpus/log/build.log"
buildTestDebug = ".././gradlew clean build --debug --refresh-dependencies > src/main/java/com/rumpus/rumpus/log/build.log"
test = ".././gradlew test > src/test/java/com/rumpus/rumpus/log/test.log"
run = "./gradlew bootRun --args='--app=rumpus' &> src/main/java/com/rumpus/rumpus/log/spring.log"
run_rumpus = "./gradlew bootRun --args='--app=rumpus' &> src/main/java/com/rumpus/rumpus/log/spring.log"
run_chuck = "./gradlew bootRun --args='--app=chuck' &> src/main/java/com/rumpus/rumpus/log/spring.log"
run_bs = "./gradlew bootRun --args='--app=buildshift' &> src/main/java/com/rumpus/rumpus/log/spring.log"
runDebug = "./gradlew bootRun --debug > src/main/java/com/rumpus/rumpus/log/spring.log"
moveNodeModules = "mv -vf ./frontend/node_modules ./"
movePackage = "mv -vf ./frontend/package-lock.json ./"
dependencies = ".././gradlew dependencies > src/main/java/com/rumpus/rumpus/log/dependencies.log"


def compile_and_run_tool(tool_name):
    # make the out directory if it doesn't exist
    os.system(f'mkdir -p {tools_dir}/out')
    # Compile the tool
    compile_cmd = f'javac -cp "{build_dir}:{common_dependencies}" -d {tools_dir} {src_dir}/com/rumpus/rumpus/tools/{tool_name}.java > {tools_dir}/out/{tool_name}_compile.out 2>&1'
    os.system(compile_cmd)

    # Run the tool
    run_cmd = f'java -cp "{build_dir}:{common_dependencies}:{tools_dir}" com.rumpus.rumpus.tools.{tool_name} > {tools_dir}/out/{tool_name}_run.out 2>&1'
    os.system(run_cmd)


def compile_and_run_tool_with_input(tool_name, input_file):
    # make the out directory if it doesn't exist
    os.system(f'mkdir -p {tools_dir}/out')
    # Compile the tool
    compile_cmd = f'javac -cp "{build_dir}:{common_dependencies}" -d {tools_dir} {src_dir}/com/rumpus/rumpus/tools/{tool_name}.java > {tools_dir}/out/{tool_name}_compile.out 2>&1'
    os.system(compile_cmd)

    # Run the tool
    run_cmd = f'java -cp "{build_dir}:{common_dependencies}:{tools_dir}" com.rumpus.rumpus.tools.{tool_name} < {input_file} > {tools_dir}/out/{tool_name}_run.out 2>&1'
    os.system(run_cmd)


def commands():
    pass


if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("Usage: python runner.py [command] [tool_name]")
        sys.exit(1)

    command = sys.argv[1]

    if command == "build":
        os.system(build)
    elif command == "buildXlint":
        os.system(buildXlint)
    elif command == "buildTest":
        os.system(buildTest)
    elif command == "buildDebug":
        os.system(buildDebug)
    elif command == "buildTestInfo":
        os.system(buildTestInfo)
    elif command == "buildTestDebug":
        os.system(buildTestDebug)
    elif command == "test":
        os.system(test)
    elif command == "run":
        os.system(run)
    elif command == "run_rumpus":
        os.system(run_rumpus)
    elif command == "run_chuck":
        os.system(run_chuck)
    elif command == "run_bs":
        os.system(run_bs)
    elif command == "runDebug":
        os.system(runDebug)
    elif command == "locust_test":
        os.system(locust_test)
    elif command == "locust_version":
        os.system(locust_version)
    elif command == "dependencies":
        os.system(dependencies)
    elif command == "compileTool":
        if len(sys.argv) < 3:
            print("Usage: python runner.py compileTool [tool_name]")
            sys.exit(1)
        tool_name = sys.argv[2]
        compile_and_run_tool(tool_name)
    elif command == "runTool":
        if len(sys.argv) < 3:
            print("Usage: python runner.py runTool [tool_name]")
            sys.exit(1)
        tool_name = sys.argv[2]
        compile_and_run_tool(tool_name)
    elif command == "runToolWithInput":
        if len(sys.argv) < 4:
            print(
                "Usage: python runner.py runToolWithInput [tool_name] [input_file]")
            sys.exit(1)
        tool_name = sys.argv[2]
        input_file = sys.argv[3]
        compile_and_run_tool_with_input(tool_name, input_file)
    else:
        print("Error: bad argument")
        sys.exit(1)
