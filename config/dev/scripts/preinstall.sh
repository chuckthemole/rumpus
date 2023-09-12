#!/bin/bash

rm -f ./src/main/js/common/*

# make venv, activate, and install requirements
mkdir -p ./../rumpusenv
python3 -m venv ./../rumpusenv
source ./../rumpusenv/bin/activate
pip install -r ./config/dev/requirements.txt