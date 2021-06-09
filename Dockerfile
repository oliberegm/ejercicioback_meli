
FROM maven:3.8-openjdk-11  AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
RUN mvn -B dependency:go-offline -f /tmp/pom.xml -s /usr/share/maven/ref/settings-docker.xml
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package

FROM adoptopenjdk/openjdk11:latest
EXPOSE 80

RUN mkdir /app
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/*.jar /app/meliapp.jar

ENTRYPOINT ["java","-jar","/app/meliapp.jar"]