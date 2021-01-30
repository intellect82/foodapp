# ONLINE food-ordering
Online Food Ordering Web/Mobile System with SpringMVC,COR,Spring Boot, MySQL, Redis Pub/Sub

### Prerequisites / Software Tools 

  [MySQL] (https://dev.mysql.com/doc/mysql-getting-started/en/) - MySQL Local setup
  [Maven] (http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) - Maven local setu
  [Redis] (https://redis.io/topics/quickstart) - Redis server and client Local setup


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
mvn clean install / mvn clean package 
```
* Run the project with following command:
```
mvn spring-boot:run
```
* To see the documentation of entire API, and try them out, type following url in the browser: 
```
http://localhost:8080/swagger-ui.html

SWAGGER API 's 

http://localhost:8080/api/menuitem/6 ( item Id ) 
http://localhost:8080/api/restaurantmenu/10  (menu Id ) 
http://localhost:8080/api/restaurant/11 ( restaurent Id )
http://localhost:8080/api/restaurantmenus 
```


