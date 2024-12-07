FROM openjdk:11-jre
LABEL authors="fmartinsf"

WORKDIR /app

COPY service/target/*.jar /app/api.jar

EXPOSE 8080

CMD ["java", "-jar", "api.jar"]
