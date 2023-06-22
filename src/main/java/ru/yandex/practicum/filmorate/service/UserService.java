package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserService {
    User createUser(User user);

    User updateUser(User user);

    User getUserById(int id);

    Collection<User> getUsers();

    void addFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);
}
