package com.omc.service.impl;

import com.omc.model.Todo;
import com.omc.model.User;
import com.omc.repository.TodoRepository;
import com.omc.repository.UserRepository;
import com.omc.service.interfaces.TodoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TodoService implements TodoServiceInterface {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Page<Todo> getTodoList(int page, String usernameFilter, String titleFilter, String sortField, String sortDir) {
        /*Page and sort*/
        int numRows = 10;
        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortField);
        Pageable pageable = PageRequest.of(page, numRows, sort);
        Page<Todo> todoPage;

        /*Apply filter of title or username*/
        if (usernameFilter != null && !usernameFilter.isEmpty()) {
            todoPage = todoRepository.findByUserUsername(usernameFilter, pageable);
        } else if (titleFilter != null && !titleFilter.isEmpty()) {
            todoPage = todoRepository.findByTitleContaining(titleFilter, pageable);
        } else {
            todoPage = todoRepository.findAll(pageable);
        }
        return todoPage;
    }

    @Override
    public Map<String, Object> getTodoEditor() {
        Map<String, Object> dataEditorMap = new HashMap<>();
        List<User> users = userRepository.findAll();
        dataEditorMap.put("todo", new Todo());
        dataEditorMap.put("users", users);
        return dataEditorMap;
    }

    @Override
    public void saveTodo(Todo todo, long id) throws Exception {
        todo.setId(id);
        User user = userRepository.findById(todo.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        todo.setUser(user);
        if (todo.getId() != 0 && todoRepository.existsById(todo.getId())) {
            Todo existingTodo = todoRepository.findById(todo.getId()).get();
            existingTodo.setTitle(todo.getTitle());
            existingTodo.setCompleted(todo.isCompleted());
            existingTodo.setUser(user);
            todoRepository.save(existingTodo);
        } else {
            todoRepository.save(todo);
        }
    }

    @Override
    public Map<String, Object> editTodo(long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Map<String, Object> dataEditorMap = new HashMap<>();
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Todo not found"));
        if (!todo.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("Unauthorized access");
        }
        List<User> users = userRepository.findAll();
        dataEditorMap.put("todo", todo);
        dataEditorMap.put("users", users);
        return dataEditorMap;
    }

    @Override
    public void deleteTodo(long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Todo not found"));
        if (!todo.getUser().getUsername().equals(username)){
            throw new IllegalArgumentException("Unauthorized access");
        }
        todoRepository.deleteById(id);
    }
}
