package com.omc.controller.interfaces;

import com.omc.model.Todo;
import org.springframework.ui.Model;

public interface TodoControllerInterface {
    String getTodoList(int page, String usernameFilter, String titleFilter, String sortField, String sortDir, Model model);
    String getTodoEditor(Model model);
    String saveTodo(Todo todo, long id, Model model);
    String editTodo(long id, Model model);
    String deleteTodo(long id, Model model);
}
