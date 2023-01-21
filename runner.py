# runner.py

import os
import sys

# Commands
build = "./gradlew clean build --refresh-dependencies -x test > src/main/java/com/rumpus/rumpus/log/build.log"
buildTest = "./gradlew clean build --refresh-dependencies > src/main/java/com/rumpus/rumpus/log/build.log"
buildTestInfo = "./gradlew clean build --info --refresh-dependencies > src/main/java/com/rumpus/rumpus/log/build.log"
buildTestDebug = "./gradlew clean build --debug --refresh-dependencies > src/main/java/com/rumpus/rumpus/log/build.log"
test = "./gradlew test > src/test/java/com/rumpus/rumpus/log/test.log"
run = "./gradlew bootRun > src/main/java/com/rumpus/rumpus/log/spring.log"

def commands():
    pass

if __name__ == '__main__':
    print(f"Arguments count: {len(sys.argv) - 1}")
    for i, arg in enumerate(sys.argv):
        if i == 0:
            continue
        print(f"{i:>6}: {arg}")
        if arg == "build":
            os.system(build)
        elif arg == "buildTest":
            os.system(buildTest)
        elif arg == "buildTestInfo":
            os.system(buildTestInfo)
        elif arg == "buildTestDebug":
            os.system(buildTestDebug)
        elif arg == "test":
            os.system(test)
        elif arg == "run":
            os.system(run)
        else:
            print("Error: bad argument")
        