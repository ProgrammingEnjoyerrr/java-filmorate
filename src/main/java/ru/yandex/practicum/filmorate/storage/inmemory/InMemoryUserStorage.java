package ru.yandex.practicum.filmorate.storage.inmemory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> idToUser = new HashMap<>();
    private int id = 0;


    @Override
    public User createUser(User user) {
        ++id;
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        user.setId(id);
        idToUser.put(id, user);

        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!idToUser.containsKey(user.getId())) {
            throw new UserValidationException("Попытка обновить пользователя " +
                    "с несуществующим id " + user.getId());
        }

        idToUser.put(id, user);
        return user;
    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public Collection<User> getUsers() {
        return new ArrayList<>(idToUser.values());
    }
}
