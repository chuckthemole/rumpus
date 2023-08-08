#!/bin/bash

# make common dir and link js files in common to the new local common dir
mkdir -p ./src/main/js/common && ln ./../common/src/main/js/react/* ./src/main/js/common && ln ./../common/src/main/js/common.js ./src/main/js/common