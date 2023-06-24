package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

    @Override
    public User createUser(User user) {
        log.info("creating user {}.", user);
        return userStorage.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        log.info("updating user {}.", user);
        return userStorage.updateUser(user);
    }

    @Override
    public User getUserById(int userId) {
        log.info("searching user with id {}.", userId);
        return userStorage.getUserById(userId);
    }

    @Override
    public Collection<User> getUsers() {
        log.info("searching all users.");
        return userStorage.getUsers();
    }

    @Override
    public void addFriend(int userId, int friendId) {
        ensureUserExists(userId);
        ensureUserExists(friendId);

        log.info("adding friend with id {} for user with id {}.", userId, friendId);
        userStorage.addFriend(userId, friendId);
        log.info("adding friend with id {} for user with id {}.", friendId, userId);
        userStorage.addFriend(friendId, userId);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        ensureUserExists(userId);
        ensureUserExists(friendId);

        log.info("removing friend with id {} for user with id {}.", userId, friendId);
        userStorage.deleteFriend(userId, friendId);
        log.info("removing friend with id {} for user with id {}.", friendId, userId);
        userStorage.deleteFriend(friendId, userId);
    }

    @Override
    public Collection<User> getUserFriends(int userId) {
        User user = getUserById(userId);
        Set<Long> friendIds = user.getFriends();

        Collection<User> userFriends = new ArrayList<>();
        for (Long friendId : friendIds) {
            userFriends.add(getUserById(Math.toIntExact(friendId)));
        }
        log.info("friends of user {} : {}.", user, userFriends);

        return userFriends;
    }

    private void ensureUserExists(int userId) {
        // Если такого пользователя нет, вылетит исключение
        log.info("ensuring user with id {} exists.", userId);
        userStorage.getUserById(userId);
    }
}
