FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/trams-drivers.jar trams-drivers.jar
ENV JAVA_OPTS=""
ENV EUREKA_SERVICE_URL http://localhost:8761
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /trams-drivers.jar" ]