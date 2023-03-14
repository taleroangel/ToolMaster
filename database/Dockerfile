FROM mysql:8.0
ARG MYSQL_TCP_PORT
COPY ./scripts /docker-entrypoint-initdb.d/
EXPOSE ${MYSQL_TCP_PORT}
