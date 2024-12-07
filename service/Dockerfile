FROM openjdk:11-jre
LABEL authors="fmartinsf"

WORKDIR /app

ARG JAR_FILE

COPY target/${JAR_FILE} /app/api.jar
COPY wait-fot-it.sh /wait-for-it.sh

RUN	chmod +x /wait-for-it.sh

EXPOSE 8080

CMD ["java", "-jar", "api.jar"]
