#!/bin/bash

SRC_DIR="src/main/java"
BUILD_DIR="build/classes/java/main"
TOOLS_DIR="tools"
COMMON_DEPENDENCIES="$(gradle -q printClasspath)"

# Define base commands
BUILD=".././gradlew clean build --refresh-dependencies -x test > $SRC_DIR/com/rumpus/rumpus/log/build.log"
RUN="./gradlew bootRun &> $SRC_DIR/com/rumpus/rumpus/log/spring.log"

compile_and_run_tool() {
    tool_name=$1
    mkdir -p $TOOLS_DIR/out
    
    # Compile the tool
    javac -cp "$BUILD_DIR:$COMMON_DEPENDENCIES" -d $TOOLS_DIR $SRC_DIR/com/rumpus/rumpus/tools/$tool_name.java > $TOOLS_DIR/out/${tool_name}_compile.out 2>&1
    
    # Run the tool
    java -cp "$BUILD_DIR:$COMMON_DEPENDENCIES:$TOOLS_DIR" com.rumpus.rumpus.tools.$tool_name > $TOOLS_DIR/out/${tool_name}_run.out 2>&1
}

case $1 in
    "build")
        eval $BUILD
        ;;
    "run")
        eval $RUN
        ;;
    "compileTool")
        if [ -z "$2" ]; then
            echo "Usage: $0 compileTool [tool_name]"
            exit 1
        fi
        compile_and_run_tool $2
        ;;
    "runTool")
        if [ -z "$2" ]; then
            echo "Usage: $0 runTool [tool_name]"
            exit 1
        fi
        compile_and_run_tool $2
        ;;
    *)
        echo "Error: bad argument"
        exit 1
        ;;
esac
