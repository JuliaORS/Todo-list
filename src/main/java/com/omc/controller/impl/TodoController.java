package com.omc.controller.impl;

import com.omc.controller.interfaces.TodoControllerInterface;
import com.omc.model.Todo;
import com.omc.service.impl.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
public class TodoController implements TodoControllerInterface {
    @Autowired
    TodoService todoService;

    @Override
    @GetMapping("/todo-list")
    public String getTodoList(@RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "usernameFilter", required = false) String usernameFilter,
                           @RequestParam(name = "titleFilter", required = false) String titleFilter,
                           @RequestParam(name = "sortField", defaultValue = "title") String sortField,
                           @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
                           Model model) {

        Page<Todo> todoPage = todoService.getTodoList(page, usernameFilter, titleFilter, sortField, sortDir);

        model.addAttribute("todos", todoPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", todoPage.getTotalPages());
        model.addAttribute("usernameFilter", usernameFilter);
        model.addAttribute("titleFilter", titleFilter);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        return "todo-list";
    }

    @Override
    @GetMapping("/todo-editor")
    public String getTodoEditor(Model model) {
        Map<String, Object> editorMap = todoService.getTodoEditor();
        model.addAttribute("todo", editorMap.get("todo"));
        model.addAttribute("users", editorMap.get("users"));
        return "todo-editor";
    }

    @Override
    @PostMapping("/save-todo")
    public String saveTodo(@Valid @ModelAttribute Todo todo, BindingResult bindingResult, long id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Title size must be between 1 and 200.");
            return "error-page";
        }
        try {
            todoService.saveTodo(todo, id);
            return "home";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error-page";
        }
    }

    @Override
    @GetMapping("/edit-todo")
    public String editTodo(@RequestParam("id") long id, Model model) {
        try {
            Map<String, Object> todoEditor = todoService.editTodo(id);
            model.addAttribute("todo", todoEditor.get("todo"));
            model.addAttribute("users", todoEditor.get("users"));
            return "todo-editor";
        } catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "error-page";
        }
    }

    @Override
    @PostMapping("/delete-todo")
    public String deleteTodo(@RequestParam("id") long id, Model model) {
        try {
            todoService.deleteTodo(id);
            return "redirect:/todo-list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error-page";
        }
    }
}
