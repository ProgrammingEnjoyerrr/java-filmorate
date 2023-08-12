package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FilmDbStorageTest {

    private final FilmDbStorage filmDbStorage;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final UserDbStorage userStorage;

    private static final int ANY_ID = 0;

    @Test
    void createFilm() {
        Film film = Film.builder()
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(LocalDate.of(1967, 3, 25))
                .duration(100)
                .build();
        film.setMpa(new Mpa(1, null));
        film.setGenres(List.of(new Genre(6, null)));

        Film created = filmDbStorage.createFilm(film);

        assertEquals(1, created.getId());
        assertEquals(film.getName(), created.getName());
        assertEquals(film.getDescription(), created.getDescription());
        assertEquals(film.getReleaseDate(), created.getReleaseDate());
        assertEquals(film.getDuration(), created.getDuration());
        assertEquals(new Mpa(1, "G"), created.getMpa());
        assertEquals(List.of(new Genre(6, "Боевик")), created.getGenres());
        assertEquals(film.getGenres(), created.getGenres());
        assertEquals(film.getUserLikes(), created.getUserLikes());

        film = Film.builder()
                .id(film.getId())
                .name("Film Updated")
                .description("New film update decription")
                .releaseDate(LocalDate.of(1989, 4, 17))
                .duration(190)
                .build();
        film.setMpa(new Mpa(5, null));
        film.setGenres(List.of(new Genre(2, null)));

        Film updated = filmDbStorage.updateFilm(film);
        assertEquals(1, updated.getId());
        assertEquals(film.getName(), updated.getName());
        assertEquals(film.getDescription(), updated.getDescription());
        assertEquals(film.getReleaseDate(), updated.getReleaseDate());
        assertEquals(film.getDuration(), updated.getDuration());
        assertEquals(new Mpa(5, "NC-17"), updated.getMpa());
        assertEquals(List.of(new Genre(2, "Драма")), updated.getGenres());
        assertEquals(film.getUserLikes(), updated.getUserLikes());
    }

    @Test
    void postManScenario() {
        Film film = Film.builder()
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(LocalDate.of(1967, 3, 25))
                .duration(100)
                .build();
        film.setMpa(new Mpa(1, null));

        Film created = filmDbStorage.createFilm(film);

        assertEquals(1, created.getId());
        assertEquals(film.getName(), created.getName());
        assertEquals(film.getDescription(), created.getDescription());
        assertEquals(film.getReleaseDate(), created.getReleaseDate());
        assertEquals(film.getDuration(), created.getDuration());
        assertEquals(new Mpa(1, "G"), created.getMpa());
        //assertEquals(List.of(new Genre(6, "Боевик")), created.getGenres());
        assertEquals(film.getGenres(), created.getGenres());
        assertEquals(film.getGenres(), created.getGenres());
        assertEquals(film.getUserLikes(), created.getUserLikes());

        film = Film.builder()
                .id(film.getId())
                .name("Film Updated")
                .description("New film update decription")
                .releaseDate(LocalDate.of(1989, 4, 17))
                .duration(190)
                .build();
        film.setMpa(new Mpa(4, null));
        //film.setGenres(List.of(new Genre(2, null)));

        Film updated = filmDbStorage.updateFilm(film);
        assertEquals(1, updated.getId());
        assertEquals(film.getName(), updated.getName());
        assertEquals(film.getDescription(), updated.getDescription());
        assertEquals(film.getReleaseDate(), updated.getReleaseDate());
        assertEquals(film.getDuration(), updated.getDuration());
        assertEquals(new Mpa(4, "R"), updated.getMpa());
        //assertEquals(List.of(new Genre(2, "Драма")), updated.getGenres());
        assertEquals(film.getGenres(), updated.getGenres());
        assertEquals(film.getUserLikes(), updated.getUserLikes());

        film = Film.builder()
                .id(film.getId())
                .name("Film Updated")
                .description("New film update decription")
                .releaseDate(LocalDate.of(1989, 4, 17))
                .duration(190)
                .build();
        film.setMpa(new Mpa(5, null));
        film.setGenres(List.of(new Genre(2, null)));

        updated = filmDbStorage.updateFilm(film);
        assertEquals(1, updated.getId());
        assertEquals(film.getName(), updated.getName());
        assertEquals(film.getDescription(), updated.getDescription());
        assertEquals(film.getReleaseDate(), updated.getReleaseDate());
        assertEquals(film.getDuration(), updated.getDuration());
        assertEquals(new Mpa(5, "NC-17"), updated.getMpa());
        assertEquals(List.of(new Genre(2, "Драма")), updated.getGenres());
        //assertEquals(film.getGenres(), updated.getGenres());
        assertEquals(film.getUserLikes(), updated.getUserLikes());

        // Film id=1 update remove genre
        film = Film.builder()
                .id(film.getId())
                .name("Film Updated")
                .releaseDate(LocalDate.of(1989, 4, 17))
                .description("New film update decription")
                .duration(190)
                .build();
        film.setMpa(new Mpa(5, null));
        film.setGenres(new ArrayList<>());

        updated = filmDbStorage.updateFilm(film);
        assertEquals(1, updated.getId());
        assertEquals(film.getName(), updated.getName());
        assertEquals(film.getDescription(), updated.getDescription());
        assertEquals(film.getReleaseDate(), updated.getReleaseDate());
        assertEquals(film.getDuration(), updated.getDuration());
        assertEquals(new Mpa(5, "NC-17"), updated.getMpa());
        //assertEquals(List.of(new Genre(2, "Драма")), updated.getGenres());
        assertEquals(film.getGenres(), updated.getGenres());
        assertEquals(film.getUserLikes(), updated.getUserLikes());
        // end of "Film id=1 update remove genre"
    }

    @Test
    void updateFilm() {
    }

    @Test
    void getFilms() {
    }

    @Test
    void getFilmById() {
    }

    @Test
    void addUserLike() {
        {
            // films -> Film id=1 create
            Film film1Create = Film.builder()
                    .name("nisi eiusmod")
                    .description("adipisicing")
                    .releaseDate(LocalDate.of(1967, 3, 25))
                    .duration(100)
                    .build();
            film1Create.setMpa(new Mpa(1, null));

            Film created = filmDbStorage.createFilm(film1Create);

            assertEquals(1, created.getId());
            assertEquals(film1Create.getName(), created.getName());
            assertEquals(film1Create.getDescription(), created.getDescription());
            assertEquals(film1Create.getReleaseDate(), created.getReleaseDate());
            assertEquals(film1Create.getDuration(), created.getDuration());
            assertEquals(new Mpa(1, "G"), created.getMpa());
            assertEquals(film1Create.getGenres(), created.getGenres());
            assertEquals(film1Create.getGenres(), created.getGenres());
            assertEquals(film1Create.getUserLikes(), created.getUserLikes());
        }

        {
            // films -> Film update
            Film film1Update = Film.builder()
                    .id(1)
                    .name("Film Updated")
                    .releaseDate(LocalDate.of(1989, 4, 17))
                    .description("New film update decription")
                    .duration(190)
                    .build();
            film1Update.setMpa(new Mpa(2, null));

            Film updated = filmDbStorage.updateFilm(film1Update);
            assertEquals(1, updated.getId());
            assertEquals(film1Update.getName(), updated.getName());
            assertEquals(film1Update.getDescription(), updated.getDescription());
            assertEquals(film1Update.getReleaseDate(), updated.getReleaseDate());
            assertEquals(film1Update.getDuration(), updated.getDuration());
            assertEquals(new Mpa(2, "PG"), updated.getMpa());
            assertEquals(film1Update.getGenres(), updated.getGenres());
            assertEquals(film1Update.getUserLikes(), updated.getUserLikes());
        }

        {
            // film -> Film id=2 create
            Film film2 = Film.builder()
                    .name("New film")
                    .releaseDate(LocalDate.of(1999, 4, 30))
                    .description("New film about friends")
                    .duration(120)
                    .build();
            film2.setMpa(new Mpa(3, null));
            film2.setGenres(List.of(new Genre(1, null)));

            Film created = filmDbStorage.createFilm(film2);

            assertEquals(2, created.getId());
            assertEquals(film2.getName(), created.getName());
            assertEquals(film2.getDescription(), created.getDescription());
            assertEquals(film2.getReleaseDate(), created.getReleaseDate());
            assertEquals(film2.getDuration(), created.getDuration());
            assertEquals(new Mpa(3, "PG-13"), created.getMpa());
            assertEquals(List.of(new Genre(1, "Комедия")), created.getGenres());
            assertEquals(film2.getUserLikes(), created.getUserLikes());
        }

        {
            // add user1
            final User user = new User(ANY_ID, "dolore ullamco", "some name", "yandex@mail.ru",
                    LocalDate.parse("1946-08-20", FORMATTER));

            final User created = userStorage.createUser(user);

            // like -> Film id=2 add Like from user id=1
            filmDbStorage.addUserLike(2, 1);

            // like -> Film most popular film
            List<Film> mostPopular = new ArrayList<>(filmDbStorage.getPopularFilms(1));
            assertNotNull(mostPopular);
            assertEquals(1, mostPopular.size());

            Film got = mostPopular.get(0);
            assertEquals(2, got.getId());
        }
    }

    @Test
    void deleteUserLike() {
    }

    @Test
    void getPopularFilms() {
    }
}