# Test task for spherum

To run you must have java-17.

Run:
```
mvn clean install spring-boot:repackage -DskipTests
java -jar ./target/testmail-1.0.0.jar
```

If you want to run it in docker container, then:

```
mvn clean install spring-boot:repackage -DskipTests
docker build -t testmail .
docker run -p 8080:8080 -d testmail
```

You also might run existing container:

```
docker run -p 8080:8080 -d medianik5/testmail
```