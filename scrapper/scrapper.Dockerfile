FROM openjdk:21

# Set working directory
WORKDIR /app/scrapper

COPY target/scrapper.jar .

ENTRYPOINT java -jar scrapper.jar
