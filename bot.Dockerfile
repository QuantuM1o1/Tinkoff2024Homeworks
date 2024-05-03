FROM maven:3.9.6 AS build

# Set working directory
WORKDIR /build

COPY pom.xml .

COPY /bot/src ./bot/src
COPY /bot/pom.xml ./bot/pom.xml

COPY /models/src ./models/src
COPY /models/pom.xml ./models/pom.xml

COPY /scrapper/src ./scrapper/src
COPY /scrapper/pom.xml ./scrapper/pom.xml

COPY /scrapper-jooq/src ./scrapper-jooq/src
COPY /scrapper-jooq/pom.xml ./scrapper-jooq/pom.xml

RUN mvn clean package -Dmaven.test.skip=true -pl bot -am


FROM openjdk:21

# Set working directory
WORKDIR /app

COPY --from=build /build/bot/target/bot.jar .

ENTRYPOINT ["java", "-jar", "bot.jar"]
