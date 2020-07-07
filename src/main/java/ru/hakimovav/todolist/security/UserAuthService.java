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

// Данный класс отвечает за авторизацию пользователей
@Service
@Transactional
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    public UserAuthService(UserRepository userRepository) { // 44. Создаем конструктор
        this.userRepository = userRepository;
    }

    // На основе метода loadUserByUsername из интерфейса UserDetails прописываем извлечение данных из БД для авторизации
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username) // Извлекаем по имени
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("USER"))))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}