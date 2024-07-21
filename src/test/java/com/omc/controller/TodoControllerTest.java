package com.omc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class TodoControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    TodoService todoService;
    private MockMvc mockMvc;
    private User user;
    private Todo todo;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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
    public void getTodoListFilterByTitleTest() throws Exception {
        mockMvc.perform(get("/todo-list")
                        .param("page", "0")
                        .param("usernameFilter", "")
                        .param("titleFilter", "")
                        .param("sortField", "title")
                        .param("sortDir", "asc"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("usernameFilter", ""))
                .andExpect(model().attribute("titleFilter", ""))
                .andExpect(model().attribute("sortField", "title"))
                .andExpect(model().attribute("sortDir", "asc"))
                .andExpect(view().name("todo-list"));;
    }

    @Test
    public void getTodoEditorTest() throws Exception {
        mockMvc.perform(get("/todo-editor"))
                .andExpect(status().isOk())
                .andExpect(view().name("todo-editor"))
                .andExpect(model().attributeExists("todo"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    public void saveTodoExistingIdTest() throws Exception {
        mockMvc.perform(post("/save-todo")
                        .param("title", "new title")
                        .param("user.id", String.valueOf(user.getId()))
                        .param("id", String.valueOf(todo.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    public void saveTodoNotExistingIdTest() throws Exception {
        mockMvc.perform(post("/save-todo")
                        .param("title", "new title")
                        .param("user.id", "-1")
                        .param("id", "0"))
                .andExpect(view().name("error-page"));
    }

    @Test
    public void editTodoTest() throws Exception {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(user.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(get("/edit-todo")
                        .param("id", String.valueOf(todo.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("todo-editor"));
    }

    @Test
    public void editTodoWrongIdTest() throws Exception {
        mockMvc.perform(get("/edit-todo")
                        .param("id", "-1"))
                .andExpect(view().name("error-page"));
    }

    @Test
    public void deleteTodoTest() throws Exception {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(user.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(post("/delete-todo")
                        .param("id", String.valueOf(todo.getId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/todo-list"));
    }

    @Test
    public void deleteTodoWrongIdTest() throws Exception {
        mockMvc.perform(post("/delete-todo")
                        .param("id", "-1"))
                .andExpect(view().name("error-page"));
    }
}
