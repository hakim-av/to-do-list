package ru.hakimovav.todolist.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.hakimovav.todolist.persist.entity.User;
import ru.hakimovav.todolist.persist.repo.UserRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
public class UserAuthService implements UserDetailsService { // 43. Создаем сервис авторизации пользователей, добавляем аннотации

    private final UserRepository userRepository;

    @Autowired
    public UserAuthService(UserRepository userRepository) { // 44. Создаем конструктор
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // 42. Создаем метод
        Optional<User> optUser = userRepository.getUserByUsername(username); // 47. Извлекаем по имени
        if (!optUser.isPresent()) { // 48. Проверяем есть ли такой пользователь
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User( // 49. Если есть данный юзер
                optUser.get().getUsername(),
                optUser.get().getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("USER")) // 50. Задаем роль пользователя
        );
    }
}