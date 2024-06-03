FROM eclipse-temurin:17-jdk

EXPOSE 8080

COPY ./build/libs/code-challenge-cart-api-1.0-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "code-challenge-cart-api.jar"]
