package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserStorage storage;

    @Override
    public User createUser(User user) {
        return storage.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        return storage.updateUser(user);
    }

    @Override
    public User getUserById(int id) {
        return storage.getUserById(id);
    }

    @Override
    public Collection<User> getUsers() {
        return storage.getUsers();
    }

    @Override
    public void addFriend(int userId, int friendId) {
        // Если нет таких пользователей, вылетит исключение
        final User user = storage.getUserById(userId);
        final User friend = storage.getUserById(friendId);

        storage.addFriend(userId, friendId);
        storage.addFriend(friendId, userId);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        // Если нет таких пользователей, вылетит исключение
        final User user = storage.getUserById(userId);
        final User friend = storage.getUserById(friendId);

        storage.deleteFriend(userId, friendId);
    }

    @Override
    public Collection<User> getUserFriends(int userId) {
        User user = getUserById(userId);
        Set<Long> friendIds = user.getFriends();

        Collection<User> userFriends = new ArrayList<>();
        for (Long friendId : friendIds) {
            userFriends.add(getUserById(Math.toIntExact(friendId)));
        }

        return userFriends;
    }
}
