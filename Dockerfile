FROM openjdk:11
VOLUME /tmp
EXPOSE 8082
ADD ./target/CustomerProduct-0.0.1-SNAPSHOT.jar ms-customerproduct.jar
ENTRYPOINT ["java","-jar","/ms-customerproduct.jar"]