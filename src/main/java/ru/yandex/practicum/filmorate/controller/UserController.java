package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.debug("got request POST /users");
        log.debug("request body: {}", user);

        User created = service.createUser(user);
        log.debug("user created");

        return created;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.debug("got request PUT /users");
        log.debug("request body: {}", user);

        User updated = service.updateUser(user);
        log.debug("film updated");

        return updated;
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return service.getUsers();
    }

    @GetMapping(value = "/{id}")
    public User getUserById(@PathVariable("id") String userIdStr) {
        int userId = Integer.parseInt(userIdStr);

        return service.getUserById(userId);
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") String userIdStr,
                          @PathVariable("friendId") String friendIdStr) {
        int userId = Integer.parseInt(userIdStr);
        int friendId = Integer.parseInt(friendIdStr);

        service.addFriend(userId, friendId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") String userIdStr,
                             @PathVariable("friendId") String friendIdStr) {
        int userId = Integer.parseInt(userIdStr);
        int friendId = Integer.parseInt(friendIdStr);

        service.deleteFriend(userId, friendId);
    }

    @GetMapping(value = "/{id}/friends")
    public Collection<User> getUserFriends(@PathVariable("id") String userIdStr) {
        int userId = Integer.parseInt(userIdStr);

        User user = service.getUserById(userId);
        Set<Long> friendIds = user.getFriends();

        Collection<User> userFriends = new ArrayList<>();
        for (Long friendId : friendIds) {
            userFriends.add(service.getUserById(Math.toIntExact(friendId)));
        }

        return userFriends;
    }
}
