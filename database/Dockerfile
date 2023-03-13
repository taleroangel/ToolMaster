FROM mysql:8.0
ARG MYSQL_TCP_PORT
COPY ./scripts /docker-entrypoint-initdb.d/
CMD ["--character-set-server=utf8mb4", "--"]
EXPOSE ${MYSQL_TCP_PORT}