FROM maven:3.8-openjdk-11  AS MAVEN_TOOL_CHAIN
RUN git clone https://github.com/oliberegm/ejercicioback_meli.git /myapp
RUN mkdir /home/app
RUN cp -R /myapp/* /home/app

#COPY /app/pom.xml /tmp/
RUN cp /home/app/pom.xml /tmp
RUN mvn -B dependency:go-offline -f /tmp/pom.xml -s /usr/share/maven/ref/settings-docker.xml
#COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package

FROM adoptopenjdk/openjdk11:latest
EXPOSE 8080
RUN mkdir /app
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/*.jar /app/meliapp.jar

ENTRYPOINT ["java","-jar","/app/meliapp.jar"]