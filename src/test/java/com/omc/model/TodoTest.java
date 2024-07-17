package com.omc.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TodoTest {
    @Test
    public void todoConstructorTest(){
        Address address = new Address("First street", "Barcelona", "000001", "Spain");
        User user = new User("Julia", "julia11", "123456hola", address);

        Todo todo = new Todo("todo item num1", user);
        assertNotNull(todo);
        assertEquals("Julia", todo.getUser().getName());
    }
}
