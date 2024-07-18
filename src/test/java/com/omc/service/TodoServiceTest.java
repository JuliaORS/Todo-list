package com.omc.service;

import com.omc.model.Address;
import com.omc.model.Todo;
import com.omc.model.User;
import com.omc.repository.TodoRepository;
import com.omc.repository.UserRepository;
import com.omc.service.impl.TodoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class TodoServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    TodoService todoService;
    private User user;
    private Todo todo;

    @BeforeEach
    public void setUp(){
        Address address = new Address("One street", "Paris", "000001", "France");
        user = new User("Simon", "simon15", "123456", address);
        userRepository.save(user);
        todo = new Todo("todo1", user);
        User user1 = new User("Julia", "julia11", "123456", address);
        userRepository.save(user1);
        Todo todo1 = new Todo("todo2", user1);
        todoRepository.save(todo);
        todoRepository.save(todo1);
    }

    @AfterEach
    void tearsDown(){
        todoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void getTodoListFilterByTitleTest() {
        Page<Todo> todoPage = todoService.getTodoList(0, "", "", "title", "asc");
        assertEquals(2, todoPage.getTotalElements());

        Todo firstTodo = todoPage.getContent().get(0);
        assertEquals("todo1", firstTodo.getTitle());
    }

    @Test
    public void getTodoListFilterByUsernameTest() {
        Page<Todo> todoPage = todoService.getTodoList(0, "simon15", "", "user.username", "asc");
        assertEquals(1, todoPage.getTotalElements());
    }

    @Test
    public void getTodoListEmptyDescSortTest() {
        Page<Todo> todoPage = todoService.getTodoList(0, "si", "", "user.username", "asc");
        assertEquals(0, todoPage.getTotalElements());
    }

    @Test
    public void getTodoListEmptyListTest() {
        Page<Todo> todoPage = todoService.getTodoList(0, "", "todo", "title", "desc");
        assertEquals(2, todoPage.getTotalElements());

        Todo firstTodo = todoPage.getContent().get(0);
        assertEquals("todo2", firstTodo.getTitle());
    }

    @Test
    public void getTodoEditorTest() {
        Map<String, Object> dataEditorMap = todoService.getTodoEditor();

        assertInstanceOf(Todo.class, dataEditorMap.get("todo"));
        Todo todo2 = (Todo)dataEditorMap.get("todo");
        assertEquals(0, todo2.getId());
        assertTrue(dataEditorMap.get("users") instanceof List<?>);
    }

    @Test
    public void saveTodoExistingIdTest() throws Exception {
        Todo todo3 = new Todo("todo3", user);
        todoRepository.save(todo3);
        todo3.setTitle("new title"); //set new title
        todoService.saveTodo(todo3, todo3.getId()); //update todo

        Pageable pageable = PageRequest.of(0, 3, Sort.Direction.ASC, "title");
        Page<Todo> todoPage = todoRepository.findByTitleContaining("new", pageable);
        assertEquals(1, todoPage.getTotalElements()); //check exists todo with "new" in title

        Page<Todo> todoPage1 = todoRepository.findByTitleContaining("todo3", pageable);
        assertEquals(0, todoPage1.getTotalElements()); //check not exists todo with "todo3" in title
    }

    @Test
    public void saveTodoNewIdTest() throws Exception {
        Todo todo3 = new Todo("todo3", user);
        todoService.saveTodo(todo3, todo3.getId());

        Pageable pageable = PageRequest.of(0, 3, Sort.Direction.ASC, "title");
        Page<Todo> todoPage1 = todoRepository.findByTitleContaining("todo3", pageable);
        assertEquals(1, todoPage1.getTotalElements()); //check exists todo with "todo3" in title
    }

    @Test
    public void saveTodoWrongUserIdTest() throws Exception {
        Todo todo3 = new Todo("todo3", user);
        todo3.getUser().setId(23);

        assertThrows(Exception.class, () -> {
            todoService.saveTodo(todo3, todo3.getId());});
    }

    @Test
    public void saveTodoInvalidTitleTest() throws Exception {
        Todo todo3 = new Todo("", user);

        assertThrows(Exception.class, () -> {
            todoService.saveTodo(todo3, todo3.getId());});
    }

    @Test
    public void editTodoTest() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(user.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, Object> dataEditorMap = todoService.editTodo(todo.getId());
        assertInstanceOf(Todo.class, dataEditorMap.get("todo"));
        Todo todo2 = (Todo)dataEditorMap.get("todo");
        assertTrue(dataEditorMap.get("users") instanceof List<?>);
    }

    @Test
    public void editTodoUnauthorizedUserTest() throws Exception {
        Optional<User> optionalUser = userRepository.findByUsername("julia11");
        if (optionalUser.isPresent()) {
            User user3 = optionalUser.get();
            Authentication authentication = mock(Authentication.class);
            when(authentication.getName()).thenReturn(user3.getUsername());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        assertThrows(Exception.class, () -> {
            todoService.editTodo(todo.getId());});
    }

    @Test
    public void deleteTodoTest() throws Exception {
        int sizeRepo = todoRepository.findAll().size();

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(user.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        todoService.deleteTodo(todo.getId());
        assertEquals(sizeRepo - 1, todoRepository.findAll().size());
    }

    @Test
    public void deleteTodoUnauthorizedUserTest() throws Exception {
        Optional<User> optionalUser = userRepository.findByUsername("julia11");
        if (optionalUser.isPresent()) {
            User user3 = optionalUser.get();
            Authentication authentication = mock(Authentication.class);
            when(authentication.getName()).thenReturn(user3.getUsername());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        assertThrows(Exception.class, () -> {
            todoService.deleteTodo(todo.getId());});
    }
}

