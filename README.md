# food-ordering
REST API for Online Food Ordering System with Spring Boot, MySQL, Redis

### Prerequisites
* [MySQL] (https://dev.mysql.com/doc/mysql-getting-started/en/) - MySQL Local setup
* [Redis] (https://redis.io/topics/quickstart) - Redis server and client Local setup
* [Maven] (http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) - Maven local setup

## Deployment
* Clone this repository on your system
* Import this in your favorite IDE (In eclipse - Import > Existing Maven Projects) - Maven will automatically download all the dependencies
* Create database from dump file - Create database by going to your repository location and running following command (Dump file food_order_system.sql is given in the repository): 
```
 mysql -u <mysql user name> -p food_order_system < food_order_system.sql
 ```
* Change `application.properties` file with your MySQL and Redis settings.
* Build the project by going to the project folder on command line and executing following command:
```
mvn clean install
```
* Run the project with following command:
```
mvn spring-boot:run
```
* To see the documentation of entire API, and try them out, type following url in the browser: 
```
http://localhost:8080/swagger-ui.html
```
