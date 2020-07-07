package ru.hakimovav.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.hakimovav.todolist.persist.entity.User;
import ru.hakimovav.todolist.persist.repo.UserRepository;
import ru.hakimovav.todolist.repr.UserRepr;

import javax.transaction.Transactional;
import java.util.Optional;

import static ru.hakimovav.todolist.security.Utils.getCurrentUser;

@Service
@Transactional
public class UserService {
    // Создаем бины на основе Юзер класса и реализации шифровальщика
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Реализуем логику создания нового пользователя и внесения его в БД (метод create здесь же
    public void create(UserRepr userRepr) {
        User user = new User (); // Связь с сущностью User
        user.setUsername(userRepr.getUsername());
        user.setPassword(passwordEncoder.encode(userRepr.getPassword()));
        userRepository.save(user); // Сохраняем юзера в БД
    }
    public Optional<Long> getCurrentUserId() {
        return getCurrentUser()
                .flatMap(userRepository::getUserByUsername)
                .map(User::getId);
    }
}
