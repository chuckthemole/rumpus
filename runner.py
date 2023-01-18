# runner.py

import os
import sys

# Commands
build = "./gradlew clean build --refresh-dependencies"
run = "./gradlew bootRun > src/main/java/com/rumpus/rumpus/spring.log"

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
        elif arg == "run":
            os.system(run)
        else:
            print("Error: bad argument")
        