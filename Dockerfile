FROM maven:3.8.3-openjdk-17
ADD target/springboot-mongo-docker.jar springboot-mongo-docker.jar
ENTRYPOINT ["java","-jar","/springboot-mongo-docker.jar"]