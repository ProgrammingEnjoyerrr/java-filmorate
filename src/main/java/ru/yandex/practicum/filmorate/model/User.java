package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.utils.validation.constraints.NoSpaces;
import ru.yandex.practicum.filmorate.utils.validation.constraints.NotInFuture;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
@ToString
public class User {
    @Setter
    private Integer id;

    @NotEmpty
    @NoSpaces
    private final String login;

    @Setter
    private String name;

    @Email
    private final String email;

    @NotInFuture
    private final LocalDate birthday;

    private final Set<Long> friends;

    public User(Integer id, String login, String name, String email, LocalDate birthday) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.friends = new HashSet<>();
    }

    public boolean addFriend(Long friendId) {
        return friends.add(friendId);
    }

    public void deleteFriend(Long friendId) {
        friends.remove(friendId);
    }
}
