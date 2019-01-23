FROM openjdk:8u171-jdk-alpine

ADD ./web-demo/target/web-demo-*.jar /app/demo.jar

#Java execution
CMD ["java", "-Xmx512m", \
   "-XX:+UnlockExperimentalVMOptions", \
   "-XX:+UseCGroupMemoryLimitForHeap", \
   "-jar", "/app/demo.jar"]

EXPOSE 8080
