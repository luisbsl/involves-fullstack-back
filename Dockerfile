FROM gradle:5.4.1-jdk8-alpine as compile
COPY . /home/source/java
WORKDIR /home/source/java
USER root
RUN chown -R gradle /home/source/java
USER gradle
RUN gradle clean build -x test

FROM openjdk:8-jre-alpine
WORKDIR /home/application/java
COPY --from=compile "/home/source/java/build/libs/involves-fullstack-1.0.0.jar" .
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "-Dspring.config.name=application-docker", "/home/application/java/involves-fullstack-1.0.0.jar"]