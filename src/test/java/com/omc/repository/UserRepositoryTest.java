package com.omc.repository;

import com.omc.model.Address;
import com.omc.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp(){
        Address address = new Address("One street", "Paris", "000001", "France");
        user = new User("Simon", "simon156", "123456", address);
        userRepository.save(user);
    }

    @AfterEach
    void tearsDown(){
        userRepository.deleteAll();
    }

    @Test
    void saveUserTest(){
        int initialSize = userRepository.findAll().size();
        Address address = new Address("First street", "Barcelona", "000001", "Spain");
        User user1 = new User("Julia", "julia11", "123456", address);
        userRepository.save(user1);
        assertEquals(initialSize + 1, userRepository.findAll().size());
    }

    @Test
    void deleteAlbumTest(){
        userRepository.delete(user);
        assertEquals(0, userRepository.findAll().size());
    }
}
