package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserControllerValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> idToUser = new HashMap<>();
    private int id = 0;

    @PostMapping
    @ResponseBody
    public User create(@Valid @RequestBody User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new UserControllerValidationException("Дата рождения не может быть в будущем. " +
                    "Переданное значение: " + user.getBirthday());
        }

        ++id;
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        user.setId(id);
        idToUser.put(id, user);

        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        if (!idToUser.containsKey(user.getId())) {
            throw new UserControllerValidationException("Попытка обновить пользователя " +
                    "с несуществующим id " + user.getId());
        }

        idToUser.put(id, user);
        return user;
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return idToUser.values();
    }
}
