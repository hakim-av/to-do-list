package ru.hakimovav.todolist.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/*
14. Реализуем отправку данных при нажатии на регистрацию в базу данных
 */

@Controller
public class LoginController { // 3. Регистрация

    @GetMapping("/login")
    public String loginPage() { // 4. Переход на страницу логирования
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() { // 5. Переход на страницу регистрации
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser() { // 15. Переход на страницу одного конкретного todoPage
        return "register";
    }
}
