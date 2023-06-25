package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserService {
    User createUser(User user);

    User updateUser(User user);

    User getUserById(String userId);

    Collection<User> getUsers();

    void addFriend(String userId, String friendId);

    void deleteFriend(String userId, String friendId);

    Collection<User> getUserFriends(String userId);

    Collection<User> getCommonFriends(String userId, String otherUserId);
}
