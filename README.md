# Scala Pet API

A really minimal Scala Play REST API using a Postgres database.

## Getting Started

Start the database.

```shell script
docker-compose up
```

Connect to database using connection details in application.conf.

Load the database using dummy-data.sql.

Start the REST API.

```shell script
sbt ~run
```

Try the API using Postman. Import postman_collection.json.
