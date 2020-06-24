# Ktor Boot

This is a preconfigured project, ready to develop REST-API on Ktor.

Major components:

* ktor
* postgresql
* koin - DI-framework
* dbmate - sql migration tool

Minor preconfigured components:

* hikaricp - uses for managing connection pool
* kotlin-jdbc - wrapper for JDBC driver, for easy query building
* ktor-auth-jwt - for JWT-authentication
* jackson - as json-decoding library
* jackson-datatype-jsr310 - for using JavaTime API in json
* logback - logger
* junit - for unit tests `test/unit`
* ktor-server-test-host - for functional tests `test/functional`
* shadowJar - for building single jar file on release

## How to start

Clone project, drop `.git` folder, and replace all `ktor-boot` occurrences (5 items) 
in the project to your own.

You are ready to 🚀🚀🚀

## Migrations

Migrations are based on `dbmate`: https://github.com/amacneil/dbmate

You need to install `dbmate` tool and sql-cli tools `mysqldump`, `pg_dump` or other.

For MacOS and PostgreSQL you may need to install `libpq`, which contains `pg_dump`
(`brew install libpq`).

#### Run migrations

Migrate and rollback migrations using these commands

```bash
dbmate up
dbmate --no-dump-schema up
dbmate down
```

## Tests

Start test database and apply migrations
```
docker-compose -f ./docker-compose-test.yml up -d
DATABASE_URL="postgres://postgres:test123@127.0.0.1:5433/postgres?sslmode=disable" dbmate --no-dump-schema up
```

You can run tests from IDE. 

To run tests from terminal use this command:

```
./gradlew cleanTest test
```

## Deploy

Build JAR-archive:

```
./gradlew clean shadowJar
```

JAR-archive `build/libs/app.jar` ready to deploy.

To run the app you should also create `application.conf` and `logback.xml` files on the server 
(they are excluded from archive).


# FAQ

**Q: Why Jackson?**

Jackson is stricter than Gson, it doesn't use default values for int or null for missed fields.
With Moshi I don't familiar.

**Q: Why dbmate? Why not flyway?**

Flyway can't rollback migrations in the free version. 

Also, I think it is a good thought to write migrations on pure SQL. It is very flexible and transparent.