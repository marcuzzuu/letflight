FROM openjdk:8-jre-alpine
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap","-Djava.security.egd=file:/dev/./urandom","-agentlib:jdwp=server=y,transport=dt_socket,address=9000,suspend=n","-jar","/app.jar"]
