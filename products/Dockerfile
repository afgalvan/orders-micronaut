FROM openjdk:17-jdk-alpine as base

WORKDIR /app
COPY build/libs/products-*-all.jar products.jar
EXPOSE 9000
CMD [ "java", "-jar", "products.jar" ]
