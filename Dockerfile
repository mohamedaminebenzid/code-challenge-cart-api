FROM eclipse-temurin:17-jdk

EXPOSE 8080

COPY ./build/libs/code-challenge-cart-api-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "code-challenge-cart-api.jar"]
