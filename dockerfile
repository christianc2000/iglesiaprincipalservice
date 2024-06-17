FROM openjdk:17-jdk-alpine
copy target/iglesiabackend-0.0.1-SNAPSHOT.jar servicio-iglesia-app.jar
ENTRYPOINT ["java","-jar","servicio-iglesia-app.jar"]