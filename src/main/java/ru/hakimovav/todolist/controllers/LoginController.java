package ru.hakimovav.todolist.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController { // 3. Регистрация

    @GetMapping("/")
    public String indexPage() { // 6. Переход на главную страницу
        return "index";
    }

    @GetMapping("/todo")
    public String todoPage() { // 7. Переход на страницу одного конкретного todoPage
        return "todo";
    }
}
