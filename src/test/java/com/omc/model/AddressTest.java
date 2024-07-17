package com.omc.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddressTest {
   @Test
    public void addressConstructorTest(){
        Address address = new Address("First street", "Barcelona", "000001", "Spain");
        assertNotNull(address);
        assertEquals("Barcelona", address.getCity());
    }
}
