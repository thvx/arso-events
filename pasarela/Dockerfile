FROM amazoncorretto:17

WORKDIR /app

COPY target/pasarela.jar app.jar

ENV MYSQL_HOST=mysql
ENV MYSQL_PORT=3306

EXPOSE 8083

CMD ["java", "-jar", "app.jar"]