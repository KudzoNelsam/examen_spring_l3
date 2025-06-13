# Étape 1 : builder l'application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN apt-get update && apt-get install -y ca-certificates
RUN mvn clean package -DskipTests -Dfile.encoding=UTF-8

# Étape 2 : image finale avec JDK uniquement
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Port dynamique injecté par Render
ENV PORT=8080
EXPOSE 8080

# Commande de démarrage
ENTRYPOINT ["java", "-Djavax.net.ssl.trustStore=/path/to/cacerts", "-jar", "app.jar"]