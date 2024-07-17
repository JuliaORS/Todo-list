package com.omc.service.interfaces;

import com.omc.model.Todo;
import org.springframework.data.domain.Page;
import java.util.Map;

public interface TodoServiceInterface {
    public Page<Todo> getTodoList(int page, String usernameFilter, String titleFilter, String sortField, String sortDir);
    public Map<String, Object> getTodoEditor();
    public void saveTodo(Todo todo, long id) throws Exception;
    public Map<String, Object> editTodo(long id);
    public void deleteTodo(long id);
}
