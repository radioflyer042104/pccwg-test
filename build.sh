#!/bin/bash
export JAVA_HOME="/cygdrive/c/Program Files/Java/jdk-17"
./mvnw clean package && docker-compose up --build -d
