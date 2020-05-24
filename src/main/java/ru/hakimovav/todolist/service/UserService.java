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

@Service // 29. Указывает что данный класс следует создать
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired // 30. Создаст и найдет другой класс реализующий UserRepository и передаст его в качестве параметра в конструктор
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void create(UserRepr userRepr) { // 31. Создание нового пользователя
        User user = new User ();
        user.setUsername(userRepr.getUsername());
        user.setPassword(passwordEncoder.encode(userRepr.getPassword()));
        userRepository.save(user); // 32. Сохраняем юзера
    }
    public Optional<Long> getCurrentUserId() {
        return getCurrentUser()
                .flatMap(userRepository::getUserByUsername)
                .map(User::getId);
    }
}
