FROM openjdk:20
VOLUME /tmp
EXPOSE 8081
#COPY target/WG-0.0.1-SNAPSHOT.jar /vaistramanagement.jar
#ARG JAR_FILE=target/spring-boot-docker.jar
#ADD ${JAR_FILE} app.jar
ADD target/vaistra-management-0.0.1-SNAPSHOT.jar  vaistra-management.jar
ENTRYPOINT ["java","-jar","/vaistramanagement.jar"]