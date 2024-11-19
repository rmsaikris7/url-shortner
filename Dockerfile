FROM bellsoft/liberica-runtime-container:jdk-21-stream-musl AS builder

WORKDIR /home/app
ADD . /home/app
RUN ./gradlew clean build -x test

FROM bellsoft/liberica-runtime-container:jre-21-musl

WORKDIR /home/app
EXPOSE 8080
COPY --from=builder /home/app/build/libs/*.jar UrlShortner.jar
ENTRYPOINT ["java", "-jar", "UrlShortner.jar"]