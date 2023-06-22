package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User createUser(User user);

    User updateUser(User user);

    User getUserById(int userId);

    Collection<User> getUsers();

    void addFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);
}
