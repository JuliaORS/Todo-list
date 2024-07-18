# Todo-list

## Description of the porject
This application is designed to manage todo lists for users. It allows users to create, edit, and delete their tasks efficiently.

### Features
    Create Todo: Users can create new todo items.
    Edit Todo: Modify existing todo items (restricted to owner).
    Delete Todo: Remove todo items that are no longer needed (restricted to owner).
    Mark Todo as Completed: Users can mark tasks as completed when they are finished.
    View Todo List: Easily view all todo items in a list format.
    Filter Todo: By username (exact match) or by title could conatin (containing).
    Sort Todo:  Tasks can be sorted alphabetically by title, username, or country.
    Pagination: The list displays 10 records per page and includes navigation method to move between pages.

## Setup
### 1 - Prerequisites
- IntelliJ IDEA [Download IntelliJ IDEA](https://java.tutorials24x7.com/blog/how-to-install-java-17-on-windows)
- Maven [Download Maven](https://maven.apache.org/download.cgi)
- Postman or other client-rest.

### 2 - Clone repo
```
  git clone https://github.com/JuliaORS/Todo-list.git
```
### 3 - Users
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
