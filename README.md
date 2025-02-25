# Skeleton

## Local DB
### Create and initialize the DB
```shell
docker compose -f docker/db/docker-compose.yml --profile init up -d
```
### Delete the DB
```shell
docker compose -f docker/db/docker-compose.yml --profile init down
```
### Stop the DB
```shell
docker compose -f docker/db/docker-compose.yml stop
```
### Start stopped DB
```shell
docker compose -f docker/db/docker-compose.yml start
```

### DB initialization script
DB is being initialized by the [setup.sql](./docker/db/sql/setup.sql) file

### Schema creation/migration with flyway
```shell
./gradlew flywayMigrate -i
```

### Connection details
- URL: jdbc:sqlserver://localhost:1433;database=<DB_NAME>
- db name and credentials: check [.env](./docker/db/.env) file

## Environemnt variables
Set env vars and start the app:
```sh
export PETSTORE_API_BASE_URL="https://petstore3.swagger.io"
./gradlew bootRun
```