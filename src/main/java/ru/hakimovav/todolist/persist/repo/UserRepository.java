package ru.hakimovav.todolist.persist.repo;

// 28. Создаем интерфейс UserRepository наследуясь от CrudRepository

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.hakimovav.todolist.persist.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository <User, Long> {

    boolean existsByUsername(String username); // 46. Делаем проверку
    Optional<User> getUserByUsername(String username); // 45. Генерируем выдачу юзернейм
}
