package com.omc;

import com.omc.model.Address;
import com.omc.model.Todo;
import com.omc.model.User;
import com.omc.repository.TodoRepository;
import com.omc.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class TodoListApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(TodoListApplication.class, args);

        UserRepository userRepository = context.getBean(UserRepository.class);
        TodoRepository todoRepository = context.getBean(TodoRepository.class);

        Address address = new Address("First street", "Barcelona", "000001", "Spain");
        Address address1 = new Address("Second street", "London", "000002", "UK");
        Address address2 = new Address("Third street", "Sidney", "000003", "Australia");
        User user = new User("Julia", "julia01", "123456", address);
        User user1 = new User("John", "john02", "78910", address1);
        User user2 = new User("Gina", "gina03", "34567", address2);
        User user3 = new User("Peter", "peter04", "34567", address2);

        userRepository.saveAll(List.of(user, user1, user2, user3));

        Todo todo1 = new Todo("todo item num1", user1);
        Todo todo2 = new Todo("todo item num2", user2);
        Todo todo3 = new Todo("todo item num3", user);
        Todo todo4 = new Todo("todo item num4", user3);
        Todo todo5 = new Todo("todo item num5", user1);
        Todo todo6 = new Todo("todo item num6", user2);
        Todo todo7 = new Todo("todo item num7", user);
        Todo todo8 = new Todo("todo item num8", user);
        Todo todo9 = new Todo("todo item num9", user);
        Todo todo10 = new Todo("todo item num10", user3);
        Todo todo11 = new Todo("todo item num11", user3);
        Todo todo12 = new Todo("todo item num12", user);

        todoRepository.saveAll(List.of(todo1, todo2, todo3, todo4, todo5, todo6, todo7, todo8, todo9, todo10, todo11, todo12));
    }
}

