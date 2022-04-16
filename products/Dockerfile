FROM openjdk:17-jdk-alpine as base

WORKDIR /app
COPY build/libs/Productos-*-all.jar .
EXPOSE 9000
CMD [ "java", "-jar", "Productos-*-all.jar" ]

