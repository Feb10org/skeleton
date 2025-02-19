### Create and initialize the DB 
```shell
docker compose --profile init up -d
```

### Delete the DB
```shell
docker compose --profile init down
```

### Stop the DB
```shell
docker compose stop
```

### Start stopped DB
```shell
docker compose start
```

### DB initialization script
DB is being initialized by the [setup.sql](./sql/setup.sql) file

### Schema creation/migration with flyway
From project's root folder run
```
./gradlew flywayMigrate -i
```
 


### Connection details 
 - URL: jdbc:sqlserver://localhost:1433;database=<DB_NAME>
 - db name and credentials: check [.env](./.env) file

