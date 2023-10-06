FROM openjdk:17-slim
WORKDIR /app
COPY target/*.jar /app/warranty-ms.jar
EXPOSE 9081:9081
CMD ["java", "-jar", "warranty-ms.jar"]
