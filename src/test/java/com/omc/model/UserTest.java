package com.omc.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {
    @Test
    public void userConstructorTest(){
        Address address = new Address("First street", "Barcelona", "000001", "Spain");
        User user = new User("Julia", "julia11", "123456hola", address);
        assertNotNull(user);
        assertEquals("Julia", user.getName());
    }
}
