# Etapa de construcción
FROM amazoncorretto:17 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests

# Etapa de producción
FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /workspace/target/eventos-service.jar app.jar

# Variables de entorno (igual que arriba)
ENV MYSQL_HOST=mysql
ENV RABBITMQ_HOST=rabbitmq

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]