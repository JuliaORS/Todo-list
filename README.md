# Todo-list

## Description of the porject

    

## Setup
### 1 - Prerequisites
- IntelliJ IDEA [Download IntelliJ IDEA](https://java.tutorials24x7.com/blog/how-to-install-java-17-on-windows)
- Maven [Download Maven](https://maven.apache.org/download.cgi)
- Postman or other client-rest.

### 2 - Clone repo
```
  git clone https://github.com/JuliaORS/IronMusic.git
```
## Technologies Used
  - Java
  - Spring Boot: Used to create Java applications with minimal code, providing a ready-to-use execution environment.
  - Spring Security: Used for authentication and authorization in the application.
  - Hibernate: Framework that simplifies the mapping of Java objects to relational databases.
  - IntelliJ IDEA: Integrated development environment (IDE) used to develop the application.
  - Postman:  Used for testing and making HTTP requests to application's API.
  - MySQL Workbench: Used for managing and querying the MySQL database.
  - Swagger: Used to generate interactive API documentation for testing and utilizing the provided APIs.
    
    [Link to API documentation](http://localhost:8080/swagger-ui/index.html) (while application is running)

## Controllers and Routes structure
Our backend serves as the backbone, handling incoming requests, processing data, and interacting with our database through defined endpoints.

### Architecture of application
At the core of backend lies the concept of controllers, services, and repositories, orchestrated to deliver seamless communication between application and external entities such as Postman or any other client.

Controllers: These act as intermediaries, receiving HTTP requests, processing them, and delegating the appropriate actions to the corresponding service.

Services: They encapsulate the business logic of the application, orchestrating various operations to fulfill the requests received from the controllers.

Repositories: Responsible for interacting with the database, repositories provide an abstraction layer enabling seamless data manipulation.

## Resources
  - [Open api documentation](https://www.baeldung.com/spring-rest-openapi-documentation)
  - [Relationships in Spring Data REST documentation](https://www.baeldung.com/spring-data-rest-relationships)
  - [Validation request params documentation](https://www.baeldung.com/spring-validate-requestparam-pathvariable)
  - [Jakarta validation constraints documentation](jakarta.validation.constraints)
