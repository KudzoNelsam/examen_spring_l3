FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN apt-get update && apt-get install -y ca-certificates
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
RUN apt-get update && apt-get install -y ca-certificates tzdata
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]