#!/usr/bin/env bash

set -e

javac -d bin ./src/main/java/Main.java
javadoc -d doc ./src/main/java/Main.java
jar --create --file app.jar --main-class Main -C bin .
java -jar app.jar