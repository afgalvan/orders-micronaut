#!/bin/bash

./gradlew assemble
image="andresgalvan/products-app:v1.0"
docker build . -t "$image"

if [ "$1" == "push" ]; then
  docker push "$image"
fi
