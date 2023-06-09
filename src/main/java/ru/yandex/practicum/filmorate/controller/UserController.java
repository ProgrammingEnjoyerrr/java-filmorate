package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/users")
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.debug("got request POST /users");
        log.debug("request body: {}", user);

        User created = userService.createUser(user);
        log.debug("user created");

        return created;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.debug("got request PUT /users");
        log.debug("request body: {}", user);

        User updated = userService.updateUser(user);
        log.debug("film updated");

        return updated;
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        log.debug("got request GET /users");

        return userService.getUsers();
    }

    @GetMapping(value = "/{id}")
    public User getUserById(@PathVariable("id") String userIdStr) {
        log.debug("got request GET /users/{}", userIdStr);

        return userService.getUserById(userIdStr);
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") String userIdStr,
                          @PathVariable("friendId") String friendIdStr) {
        log.debug("got request PUT /users/{}/friends/{}", userIdStr, friendIdStr);

        userService.addFriend(userIdStr, friendIdStr);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") String userIdStr,
                             @PathVariable("friendId") String friendIdStr) {
        log.debug("got request DELETE /users/{}/friends/{}", userIdStr, friendIdStr);

        userService.deleteFriend(userIdStr, friendIdStr);
    }

    @GetMapping(value = "/{id}/friends")
    public Collection<User> getUserFriends(@PathVariable("id") String userIdStr) {
        log.debug("got request GET /users/{}/friends", userIdStr);

        return userService.getUserFriends(userIdStr);
    }

    @GetMapping(value = "/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable("id") String userIdStr,
                                             @PathVariable("otherId") String otherUserIdStr) {
        log.debug("got request GET /users/{}/friends/common/{}", userIdStr, otherUserIdStr);

        return userService.getCommonFriends(userIdStr, otherUserIdStr);
    }
}
