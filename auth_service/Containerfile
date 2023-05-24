FROM maven:3.9.0-amazoncorretto-17
ARG TOOLMASTER_AUTH_SERVICE_PORT

WORKDIR /app
COPY . .

RUN mvn compile
CMD mvn exec:java -Dexec.mainClass="edu.puj.toolmaster.auth.AuthServiceApplication"

EXPOSE ${TOOLMASTER_AUTH_SERVICE_PORT}