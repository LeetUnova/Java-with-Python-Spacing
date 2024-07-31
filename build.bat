@echo off
javac -d build src/leetu/pythonjava/*.java
cd build
jar cfvm JavaWithPythonSpacing.jar ../manifest.mf *