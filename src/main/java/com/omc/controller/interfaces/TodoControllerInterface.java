package com.omc.controller.interfaces;

import com.omc.model.Todo;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface TodoControllerInterface {
    String getTodoList(int page, String usernameFilter, String titleFilter, String sortField, String sortDir, Model model);
    String getTodoEditor(Model model);
    //String saveTodo(Todo todo, long id, Model model);
    String editTodo(long id, Model model);
    String deleteTodo(long id, Model model);
    public String saveTodo(@Valid @ModelAttribute Todo todo, BindingResult bindingResult, long id, Model model);
}
