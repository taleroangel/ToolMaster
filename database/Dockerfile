FROM mysql:8.0

ENV MYSQL_USER toolmaster
ENV MYSQL_PASSWORD password
ENV MYSQL_ROOT_PASSWORD password

COPY ./scripts/00_init.sql /docker-entrypoint-initdb.d/
COPY ./scripts/01_tables.sql /docker-entrypoint-initdb.d/
COPY ./scripts/02_data.sql /docker-entrypoint-initdb.d/

EXPOSE 3306