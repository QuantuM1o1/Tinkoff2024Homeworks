FROM openjdk:21

# Set working directory
WORKDIR /app/bot

COPY target/bot.jar .

ENTRYPOINT java -jar bot.jar
