package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

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
    public User create(@RequestBody User user) {
        ++id;
        user.setId(id);
        idToUser.put(id, user);

        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        return idToUser.put(id, user);
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return idToUser.values();
    }
}
