FROM amazoncorretto:17

WORKDIR /app

COPY target/eventos-service.jar app.jar

ENV MYSQL_HOST=mysql
ENV MYSQL_PORT=3306
ENV RABBITMQ_HOST=rabbitmq
ENV RABBITMQ_PORT=5672

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]