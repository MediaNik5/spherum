# Test task for spherum

## Plain java

To run you must have java-17.

Fill in gaps in application.properties

Run:
```
mvn clean install spring-boot:repackage -DskipTests
java -jar ./target/spherum-1.0.0.jar
```

## Docker

If you want to run it in docker container, then:

```
mvn clean install spring-boot:repackage -DskipTests
docker build -t spherum .
docker run -network="host" -d spherum
```

If you don't want to expose your whole network to this container, then you must create network and put 
both this and database's containers in it. Example is in docker-compose.yml file.  
Notice: you won't be able to pass config files unless you specify them in Dockerfile

You also might run existing container:

```
docker run --network="host" -e SPRING_DATASOURCE_URL=jdbc:postgresql://... -e SPRING_DATASOURCE_USERNAME=... -e SPRING_DATASOURCE_PASSWORD=... -d medianik5/spherum
```