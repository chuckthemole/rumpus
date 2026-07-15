import os
import sys
import subprocess

# Define paths
src_dir = 'src/main/java'
build_dir = 'build/classes/java/main'
tools_dir = 'tools'
common_dependencies = "$(gradle -q printClasspath)"

# Define base commands
# build = ".././gradlew clean build --refresh-dependencies -x test > src/main/java/com/rumpus/rumpus/log/build.log"
build = (
    "bash -c '../gradlew clean build --refresh-dependencies -x test "
    "> src/main/java/com/rumpus/rumpus/log/build.log "
    "2> >(sed -E "
    "\"s/([Ee][Rr][Rr][Oo][Rr])/\\x1b[31m\\1\\x1b[0m/g;"
    "s/([Ww][Aa][Rr][Nn][Ii][Nn][Gg])/\\x1b[33m\\1\\x1b[0m/g\" >&2)'"
)
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
runDebug = "./gradlew bootRun --args='--app=rumpus' --debug &> src/main/java/com/rumpus/rumpus/log/spring.log"
moveNodeModules = "mv -vf ./frontend/node_modules ./"
movePackage = "mv -vf ./frontend/package-lock.json ./"
dependencies = ".././gradlew dependencies > src/main/java/com/rumpus/rumpus/log/dependencies.log"

def runner(cmd):
    subprocess.run(["bash", "-c", cmd], check=False)

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
        runner(build)
    elif command == "buildXlint":
        runner(buildXlint)
    elif command == "buildTest":
        runner(buildTest)
    elif command == "buildDebug":
        runner(buildDebug)
    elif command == "buildTestInfo":
        runner(buildTestInfo)
    elif command == "buildTestDebug":
        runner(buildTestDebug)
    elif command == "test":
        runner(test)
    elif command == "run":
        runner(run)
    elif command == "run_rumpus":
        runner(run_rumpus)
    elif command == "run_chuck":
        runner(run_chuck)
    elif command == "run_bs":
        runner(run_bs)
    elif command == "runDebug":
        runner(runDebug)
    elif command == "locust_test":
        runner(locust_test)
    elif command == "locust_version":
        runner(locust_version)
    elif command == "dependencies":
        runner(dependencies)
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
