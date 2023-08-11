package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Qualifier("userDbStorage")
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User createUser(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        user.setId(simpleJdbcInsert.executeAndReturnKey(toMap(user)).intValue());
        return user;
    }

    @Override
    public User updateUser(User user) {
        String sqlQuery = "UPDATE users SET login = ?, name = ?, email = ?, birthday = ? WHERE user_id = ?";
        int rowsUpdated = jdbcTemplate.update(sqlQuery, user.getLogin(), user.getName(),
                user.getEmail(), user.getBirthday(), user.getId());
        if (rowsUpdated > 0) {
            return user;
        }
        throw new UserNotFoundException("Попытка обновить пользователя с несуществующим id "
                + user.getId());
    }

    @Override
    public User getUserById(int userId) {
        String sqlQuery = "SELECT user_id, login, name, email, birthday FROM users WHERE user_id=?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, userId);
        } catch (DataAccessException e) {
            throw new UserNotFoundException("Пользователь с id " + userId + " не найден.");
        }
    }

    @Override
    public Collection<User> getUsers() {
        String sqlQuery = "SELECT user_id, login, name, email, birthday FROM users";
        return jdbcTemplate.query(sqlQuery, this::mapRowToUser);
    }

    @Override
    public void addFriend(int userId, int friendId) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_friends")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("friend_id", friendId);
        map.put("friendship_status", true);

        simpleJdbcInsert.execute(map);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        String sqlQuery = "DELETE FROM user_friends where user_id = ? and friend_id = ?";

        int updatedRows = jdbcTemplate.update(sqlQuery, userId, friendId);
        if (updatedRows == 0) {
            throw new RuntimeException("Ошибка при удалении дружбы между пользоветелем" +
                    " с id = " + userId + " и другом с id = " + friendId);
        }
    }

    private Map<String, Object> toMap(User user) {
        Map<String, Object> values = new HashMap<>();
        values.put("email", user.getEmail());
        values.put("login", user.getLogin());
        values.put("name", user.getName());
        values.put("birthday", user.getBirthday());
        return values;
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id((int) resultSet.getLong("user_id"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }
}
