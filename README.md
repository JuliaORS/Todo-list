# Todo-list

## Description of the porject
This application is designed to manage todo lists for users. It allows users to create, edit, and delete their tasks efficiently.

### Features
- Create Todo: Users can create new todo items.
- Edit Todo: Modify existing todo items (restricted to owner).
- Delete Todo: Remove todo items that are no longer needed (restricted to owner).
- Mark Todo as Completed: Users can mark tasks as completed when they are finished.
- View Todo List: Easily view all todo items in a list format.
- Filter Todo: By username (exact match) or by title could conatin (containing).
- Sort Todo:  Tasks can be sorted alphabetically by title, username, or country.
- Pagination: The list displays 10 records per page and includes navigation method to move between pages.

## Setup
### OPTION 1 - Docker
#### Prerequisites
- Docker desktop [Download Docker Desktop](https://docs.docker.com/engine/install/)

#### Getting started
##### 1. Clone the Repository
```
  git clone https://github.com/JuliaORS/Todo-list.git
```
##### 3. Build and Start the Containers
You must be inside the Project Directory.
Ensure Docker Desktop is running before proceeding.
```
  docker-compose up --build
```
##### 4. Access the Application via your web browser
```
  http://localhost:8080
```
##### 5. Stop the application
```
  docker compose down
```

### OPTION 2
#### Prerequisites
- Java Development Kit (JDK) - Version: Java JDK 17 or compatible [Download Java](https://www.oracle.com/java/technologies/downloads/?er=221886)  
- Apache Maven - Maven 3.8.5 or a compatible [Download Maven](https://maven.apache.org/download.cgi)
- MySQL - Version 8.0 [Download Mysql](https://dev.mysql.com/downloads/mysql/)

#### Getting started
##### 1. Clone the Repository
```
  git clone https://github.com/JuliaORS/Todo-list.git
```
##### 2. Set Up MySQL Database
```
  CREATE DATABASE app_db;
  CREATE USER 'root'@'localhost' IDENTIFIED BY 'rootpassword';
  GRANT ALL PRIVILEGES ON app_db.* TO 'root'@'localhost';
  FLUSH PRIVILEGES;
```

##### 3. Build the Application
You must be inside the Project Directory.
```
  mvn clean package
```
##### 4. Run the Application
You must be inside the Project Directory.
```
  java -jar target/your-app-name.jar
```
##### 5. Access the Application via your web browser
```
  http://localhost:8080
```

## Users Credentials
The application includes pre-created users for login. You can use these credentials to log in to the application.
- Username: "julia01" - Password: "123456"
- Username: "john02" - Password: "78910"
- Username: "gina03" - Password: "34567"
- Username: "peter04" - Password: "34567"
    
## Architecture of application
Backend application built with Java and Spring Boot, featuring Spring Security for user management. The frontend is developed using Thymeleaf, HTML, CSS, and JavaScript. At the core of backend lies the concept of controllers, services, and repositories, orchestrated to deliver seamless communication between application and external entities.

## Resources
  - [Thymleaf documentation](https://www.thymeleaf.org/documentation.html))
  - [Spring Security documentation](https://spring.io/guides/gs/securing-web)
