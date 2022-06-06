# Test task for spherum
## Plain java

To run you must have java-17.

Fill in gaps in application.properties

Run:
```
mvn clean install spring-boot:repackage -DskipTests
java -jar ./target/spherum-1.0.0.jar
```

Go to http://localhost:8080/swagger-ui/index.html#/

You might specify log file and input json config file as follows:

```
java -jar ./target/spherum-1.0.0.jar logPath configPath
```

Config file must be in format:
```json
{
  "books": [
    {
      "author": "Стив Макконелл",
      "name": "Совершенный код",
      "price": 1000,
			"amount": 7
    },
    {
      "author": "Брюс Эккель",
      "name": "Философия Java",
      "price": 1500,
			"amount": 15
    },
    {
      "author": "Joshua Bloch",
      "name": "Effective Java",
      "price": 2500,
			"amount": 10
    }
  ]
}
```

You first must signup and login to work with api. See swagger for details.

To pop up your balance, you must use endpoint 
```http request
localhost:8080/add_money
```

Then you can buy books, see the prices, add books to the market and so on...

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