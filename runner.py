# runner.py

import os
import sys

# TODO build this out to download external dependencies (buildSrc/common)

# Commands
build = ".././gradlew clean build --refresh-dependencies -x test > src/main/java/com/rumpus/rumpus/log/build.log"
buildXlint = ".././gradlew clean build --refresh-dependencies -x test -Xlint:unchecked > src/main/java/com/rumpus/rumpus/log/build.log"
buildDebug = ".././gradlew clean build --refresh-dependencies --debug -x test > src/main/java/com/rumpus/rumpus/log/build.log"
buildTest = ".././gradlew clean build --refresh-dependencies > src/main/java/com/rumpus/rumpus/log/build.log"
buildTestInfo = ".././gradlew clean build --info --refresh-dependencies > src/main/java/com/rumpus/rumpus/log/build.log"
buildTestDebug = ".././gradlew clean build --debug --refresh-dependencies > src/main/java/com/rumpus/rumpus/log/build.log"
test = ".././gradlew test > src/test/java/com/rumpus/rumpus/log/test.log"
run = "./gradlew bootRun &> src/main/java/com/rumpus/rumpus/log/spring.log"
runDebug = "./gradlew bootRun --debug > src/main/java/com/rumpus/rumpus/log/spring.log"
moveNodeModules = "mv -vf ./frontend/node_modules ./"
movePackage = "mv -vf ./frontend/package-lock.json ./"

########### LOCUST ###############################
### locust ( https://docs.locust.io/ ) commands ##
##################################################
locust_test = "locust --locustfile ./src/main/python/test.py" # this will start the server and run the tests in test.py
locust_version = "locust -V" # this will print the version of locust


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
        elif arg == "buildXlint":
            os.system(buildXlint)
        elif arg == "buildTest":
            os.system(buildTest)
        elif arg == "buildDebug":
            os.system(buildDebug)
        elif arg == "buildTestInfo":
            os.system(buildTestInfo)
        elif arg == "buildTestDebug":
            os.system(buildTestDebug)
        elif arg == "test":
            os.system(test)
        elif arg == "run":
            os.system(run)
        elif arg == "runDebug":
            os.system(runDebug)
        elif arg == "locust_test":
            os.system(locust_test)
        elif arg == "locust_version":
            os.system(locust_version)
        else:
            print("Error: bad argument")
        