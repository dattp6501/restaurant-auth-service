FROM eclipse-temurin:17-jdk-alpine

ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD
ARG JWT_SECRET_KEY
ARG JWT_EXPIRATION_ACCESS_TOKEN
ARG JWT_EXPIRATION_REFRESH_TOKEN

ENV DB_URL=$DB_URL
ENV DB_USERNAME=$DB_USERNAME
ENV DB_PASSWORD=$DB_PASSWORD
ENV JWT_SECRET_KEY=$JWT_SECRET_KEY
ENV JWT_EXPIRATION_ACCESS_TOKEN=$JWT_EXPIRATION_ACCESS_TOKEN
ENV JWT_EXPIRATION_REFRESH_TOKEN=$JWT_EXPIRATION_REFRESH_TOKEN

VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 9000
