package ru.hakimovav.todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.hakimovav.todolist.repr.ToDoRepr;
import ru.hakimovav.todolist.service.ToDoService;
import ru.hakimovav.todolist.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static ru.hakimovav.todolist.security.Utils.getCurrentUser;

/*
1. Прописываем зависимости в pom.xml (Убираем временно, что не нужно и пересобираем пакет).
Прописываем зависимости в application.properties. Заливаем в темплейтс наши HTML.
Создаем пакет контроллеров и создаем там два класса контроллера.
8. В HTML прописываем xmlns:th="http://www.thymeleaf.org"
9. Корректируем HTML под стилистику фимлиф, добавляя th:"@{}" перед ссылками
10. Работа с моделью данных repr (Создаем пакет и 2 класса с данными, которые будут храниться
в базе данных)
12. Сделаем первый коммит на гитхаб
66. Создаем базу данных дел To Do
 */

@Controller
public class TodoController {

    private ToDoService toDoService; // 77. Создаем объект из сервиса
    private UserService userService;

    @Autowired
    public TodoController(ToDoService toDoService, UserService userService) {
        this.toDoService = toDoService;
        this.userService = userService;
    }
    @GetMapping("")
    // 6. Переход на главную страницу
    // 78. Корректируем переход на главную страницу с учетом новых блоков
    public String mainPage() {
        return "redirect:/todo/all";
    }

    @GetMapping("/todo/all") // 79. Просмотр всех to do
    public String allTodosPage(Model model) {
        List<ToDoRepr> todos = getCurrentUser()
                .map(toDoService::findToDoByUser_Username)
                .orElseThrow(IllegalStateException::new);
        model.addAttribute("todos", todos);
        return "todoList";
    }

    @GetMapping("/todo/{id}")
    public String todoPage(@PathVariable("id") Long id, Model model) {
        // 7. Переход на страницу одного конкретного todoPage
        // 80. Корректируем с учетом новых блоков
        ToDoRepr toDoRepr = toDoService.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        model.addAttribute("todo", toDoRepr);
        return "todo";
    }
    @GetMapping("/todo/create") // 81. Создаем контроллер получения нового дела
    public String createTodoPage(Model model) {
        model.addAttribute("todo", new ToDoRepr());
        return "todo";
    }

    @PostMapping("/todo/create")  // 82. Создаем контроллер создания нового дела
    public String createTodoPost(@ModelAttribute("todo") @Valid ToDoRepr toDoRepr,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "todo";
        }

        toDoService.save(toDoRepr);
        return "redirect:/";
    }

    @GetMapping("/todo/delete/{id}") // 83. Создаем контроллер удаления дела
    public String deleteTodo(@PathVariable Long id) {
        toDoService.delete(id);
        return "redirect:/";
    }
}