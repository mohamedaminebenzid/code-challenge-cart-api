FROM eclipse-temurin:17-jdk

EXPOSE 8080

COPY ./build/libs/cart-api-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "cart-api.jar"]
