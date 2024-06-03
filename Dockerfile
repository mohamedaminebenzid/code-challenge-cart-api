FROM openjdk:17-jdk

EXPOSE 8080

WORKDIR /usr/app

COPY target/cart-api-0.0.1-SNAPSHOT.jar /usr/app/cart-api.jar

ENTRYPOINT ["java", "-jar", "cart-api.jar"]
