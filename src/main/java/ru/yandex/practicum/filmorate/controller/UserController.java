package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
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
        ++id;
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        user.setId(id);
        idToUser.put(id, user);

        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (!idToUser.containsKey(user.getId())) {
            throw new UserValidationException("Попытка обновить пользователя " +
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
