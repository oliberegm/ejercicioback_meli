FROM maven:3.8-openjdk-11  AS MAVEN_TOOL_CHAIN
RUN git clone https://github.com/oliberegm/ejercicioback_meli.git /myapp
RUN mkdir /home/app
RUN cp -R /myapp/* /home/app
WORKDIR /home/app
RUN mvn -B dependency:go-offline -f pom.xml -s /usr/share/maven/ref/settings-docker.xml
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package

FROM adoptopenjdk/openjdk11:latest
EXPOSE 8080
RUN mkdir /app
RUN cp target/*.jar /app/meliapp.jar
WORKDIR /app
ENTRYPOINT ["java","-jar","/app/meliapp.jar"]