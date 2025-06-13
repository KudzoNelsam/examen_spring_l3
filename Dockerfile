# Étape 1 : Build de l'application
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copie le projet et compile
COPY . .
RUN apt-get update && apt-get install -y ca-certificates
RUN ./mvnw clean package -DskipTests

# Étape 2 : Image d'exécution
FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]