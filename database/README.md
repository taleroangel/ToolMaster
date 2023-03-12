# Database

```bash
docker build -t toolmaster_db . && \
docker run -d -p 3306:3306 -v toolmaster_db_volume:/var/lib/mysql --name="toolmaster" toolmaster_db
```