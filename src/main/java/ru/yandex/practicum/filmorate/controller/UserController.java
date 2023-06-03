package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Validated
@Slf4j
public class UserController {
    private final Map<Integer, User> idToUser = new HashMap<>();
    private int id = 0;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.debug("got request POST /users");
        log.debug("request body: {}", user);

        ++id;
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        user.setId(id);
        idToUser.put(id, user);

        log.debug("user created");
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.debug("got request PUT /users");
        log.debug("request body: {}", user);

        if (!idToUser.containsKey(user.getId())) {
            log.error("attempt to update user with unknown id: {}", user.getId());
            throw new UserValidationException("Попытка обновить пользователя " +
                    "с несуществующим id " + user.getId());
        }

        idToUser.put(id, user);
        log.debug("film updated");
        return user;
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return new ArrayList<>(idToUser.values());
    }
}
