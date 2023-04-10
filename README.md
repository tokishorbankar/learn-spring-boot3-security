# learn-spring-boot3-security


### Usage Environment

```
DB_USERNAME= DB user name here
DB_PASSWORD= DB password here
DB_URL= DB url here
DB_DRIVER_CLASS_NAME= DB driver class name here
JWT_SECRET_KEY= jwt secret key here
```
* Note : JWT secret key is HS256

### Setup MySql Server on Docker 
Local MySQL = Docker + Docker Compose + MySQL Docker image + Adminer Docker image

On folder that contains `docker-compose.yml` type one of this.

* non detach mode
```
docker-compose up
```
* detach mode
```
docker-compose up -d
```

It will spin the MySQL latest version, expose port to host at 3306 and ready connection via Adminer or `mysql` CLI command.

### Usage (stop server)

To shutdown database without remove the container.

```
docker-compose stop
```

To shutdown database and remove the container.
```
docker-compose down
```

Is data that already created will gone? No, since in the Docker Compose file you can see that we utilize data container named `mysql_db_data_container` to store the MySQL data.

### MySQL credential

- Username: root
- Password: rootpassword

### How to connect to MySQL

#### Via Adminer
Go to http://localhost:8080

#### Via command line
Make sure you have MySQL client installed and `mysql` command in CLI available.

```
mysql -uroot -prootpassword -h 127.0.0.1
```

or

```
mysql -uroot -prootpassword --protocol=TCP
```

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.5/maven-plugin/reference/html/#build-image)
* [Spring Security](https://docs.spring.io/spring-boot/docs/3.0.5/reference/htmlsingle/#web.security)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/3.0.5/reference/htmlsingle/#actuator)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.5/reference/htmlsingle/#web)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.0.5/reference/htmlsingle/#using.devtools)

### Guides
The following guides illustrate how to use some features concretely:

* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

