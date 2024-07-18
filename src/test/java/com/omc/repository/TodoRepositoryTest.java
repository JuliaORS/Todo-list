package com.omc.repository;

import com.omc.model.Address;
import com.omc.model.Todo;
import com.omc.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TodoRepositoryTest{
    @Autowired
    UserRepository userRepository;
    @Autowired
    TodoRepository todoRepository;
    private User user;
    private Todo todo;

    @BeforeEach
    public void setUp(){
        Address address = new Address("One street", "Paris", "000001", "France");
        user = new User("Simon", "simon15", "123456", address);
        userRepository.save(user);
        todo = new Todo("todo1", user);
        todoRepository.save(todo);
    }

    @AfterEach
    void tearsDown(){
        todoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void saveTodoTest(){
        int initialSize = todoRepository.findAll().size();
        todoRepository.save(new Todo("new todo", user));
        assertEquals(initialSize + 1, todoRepository.findAll().size());
    }

    @Test
    void saveTodoInvalidTitleTest(){
        assertThrows(Exception.class, () -> {
            todoRepository.save(new Todo("", user));
        });

        assertThrows(Exception.class, () -> {
            todoRepository.save(new Todo("01234567890123456789012345678901234567890123456789" +
                    "01234567890123456789012345678901234567890123456789012345678901234567890123456789" +
                    "01234567890123456789012345678901234567890123456789012345678901234567890123456789" +
                    "01234567890123456789", user));
        });
    }

    @Test
    void deleteAlbumTest(){
        todoRepository.delete(todo);
        assertEquals(0, todoRepository.findAll().size());
    }

    @Test
    void findByTitleContainingTest(){
        todoRepository.save(new Todo("new todo", user));
        Pageable pageable = PageRequest.of(0, 3, Sort.Direction.ASC, "title");
        Page<Todo> todoPage= todoRepository.findByTitleContaining("new", pageable);
        assertEquals(1, todoPage.getTotalElements());
    }

    @Test
    void findByTitleContainingNotExistingTest(){
        Pageable pageable = PageRequest.of(0, 3, Sort.Direction.ASC, "title");
        Page<Todo> todoPage= todoRepository.findByTitleContaining("new", pageable);
        assertEquals(0, todoPage.getTotalElements());
    }

    @Test
    void findByUserUsernameTest(){
        Pageable pageable = PageRequest.of(0, 3, Sort.Direction.ASC, "user.username");
        Page<Todo> todoPage= todoRepository.findByUserUsername("simon15", pageable);
        assertEquals(1, todoPage.getTotalElements());
    }

    @Test
    void findByUserUsernameNotExistingTest(){
        Pageable pageable = PageRequest.of(0, 3, Sort.Direction.ASC, "user.username");
        Page<Todo> todoPage= todoRepository.findByUserUsername("simon", pageable);
        assertEquals(0, todoPage.getTotalElements());
    }
}
