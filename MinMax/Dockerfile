FROM gradle as bulider
COPY . /app
WORKDIR /app
USER root
RUN gradle build -x test


FROM openjdk:11-jre-alpine
USER root
COPY --from=bulider /app/build/libs/*.jar /app/app.jar
EXPOSE 8000
CMD ["java", "-jar", "/app/app.jar"]