#!/bin/bash

javac -cp .:beautyeye_lnf.jar -d ../bin Gearth.java
cd ../bin/
bash run.sh
