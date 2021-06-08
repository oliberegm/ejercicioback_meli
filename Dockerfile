#LANZANDO LA APP
FROM adoptopenjdk/openjdk11:latest
MAINTAINER Oliber Garcia "oliber.garcia@gmail.com"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /usr/local/lib/meli.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/meli.jar"]
