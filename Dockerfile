FROM adoptopenjdk/openjdk11:latest
EXPOSE 8080
RUN mkdir /app
ADD https://github.com/oliberegm/ejercicioback_meli/blob/main/target/trace-0.0.1-SNAPSHOT.jar?raw=true /app
#RUN cp target/*.jar /app/meliapp.jar
WORKDIR /app
ENTRYPOINT ["java","-jar","/app/trace-0.0.1-SNAPSHOT.jar"]