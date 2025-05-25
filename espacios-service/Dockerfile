FROM amazoncorretto:17

WORKDIR /app

COPY target/espacios-service.jar app.jar

ENV MONGODB_HOST=mongodb
ENV MONGODB_PORT=27017
ENV RABBITMQ_HOST=rabbitmq
ENV RABBITMQ_PORT=5672

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]