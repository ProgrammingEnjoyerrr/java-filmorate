package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.*;

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
        log.debug("got request GET /users");
        return service.getUsers();
    }

    @GetMapping(value = "/{id}")
    public User getUserById(@PathVariable("id") String userIdStr) {
        log.debug("got request GET /users/{}", userIdStr);
        int userId = Integer.parseInt(userIdStr);

        return service.getUserById(userId);
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") String userIdStr,
                          @PathVariable("friendId") String friendIdStr) {
        log.debug("got request PUT /users/{}/friends/{}", userIdStr, friendIdStr);
        int userId = Integer.parseInt(userIdStr);
        int friendId = Integer.parseInt(friendIdStr);

        service.addFriend(userId, friendId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") String userIdStr,
                             @PathVariable("friendId") String friendIdStr) {
        log.debug("got request DELETE /users/{}/friends/{}", userIdStr, friendIdStr);
        int userId = Integer.parseInt(userIdStr);
        int friendId = Integer.parseInt(friendIdStr);

        service.deleteFriend(userId, friendId);
    }

    @GetMapping(value = "/{id}/friends")
    public Collection<User> getUserFriends(@PathVariable("id") String userIdStr) {
        log.debug("got request GET /users/{}/friends", userIdStr);
        int userId = Integer.parseInt(userIdStr);
        return service.getUserFriends(userId);
    }

    @GetMapping(value = "/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable("id") String userIdStr,
                                             @PathVariable("otherId") String otherUserIdStr) {
        log.debug("got request GET /users/{}/friends/common/{}", userIdStr, otherUserIdStr);

        int userId = Integer.parseInt(userIdStr);
        int otherUserId = Integer.parseInt(otherUserIdStr);

        Set<User> commonUsers = new HashSet<>();

        Collection<User> userFriends = service.getUserFriends(userId);
        commonUsers.addAll(userFriends);
        Collection<User> otherUserFriends = service.getUserFriends(otherUserId);
        commonUsers.retainAll(otherUserFriends);

        return new ArrayList<>(commonUsers);
    }
}
