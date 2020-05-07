package ru.hakimovav.todolist.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
1. Прописываем зависимости в pom.xml (Убираем временно, что не нужно и пересобираем пакет).
Прописываем зависимости в application.properties. Заливаем в темплейтс наши HTML.
Создаем пакет контроллеров и создаем там два класса контроллера.
8. В HTML прописываем xmlns:th="http://www.thymeleaf.org"
9. Корректируем HTML под стилистику фимлиф, добавляя th:"@{}" перед ссылками
10. Работа с моделью данных repr (Создаем пакет и 2 класса с данными, которые будут храниться
в базе данных)
 */

@Controller
public class TodoController { // 2. Взаимодействие

    @GetMapping("/login")
    public String loginPage() { // 4. Переход на страницу логирования
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() { // 5. Переход на страницу регистрации
        return "register";
    }
}
