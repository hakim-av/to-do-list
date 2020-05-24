package ru.hakimovav.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ToDoListApplication {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { // 35. Подключаем кодировщик паролей
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(ToDoListApplication.class, args);
    }
}
