package ru.hakimovav.todolist.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.hakimovav.todolist.persist.repo.UserRepository;

import javax.transaction.Transactional;
import java.util.Collections;

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
        return userRepository.getUserByUsername(username) // 47. Извлекаем по имени (48, 49, 50 пункты исключены)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("USER"))))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}