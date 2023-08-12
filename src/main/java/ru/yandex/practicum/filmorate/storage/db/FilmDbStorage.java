package ru.yandex.practicum.filmorate.storage.db;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Qualifier("filmDbStorage")
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film createFilm(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");
        film.setId(simpleJdbcInsert.executeAndReturnKey(toMap(film)).intValue());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, mpa_rating_id = ?" +
                " WHERE film_id = ?";
        int rowsUpdated = jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        if (rowsUpdated > 0) {
            return film;
        }

        throw new FilmNotFoundException("Попытка обновить фильм с несуществующим id " + film.getId());
    }

    @Override
    public Collection<Film> getFilms() {
        String sqlQuery = "SELECT film_id, name, description, release_date, duration, mpa_rating_id FROM films";
        List<FilmDAO> filmsDAOs = jdbcTemplate.query(sqlQuery, this::mapRowToFilmDAO);

        List<Film> films = new ArrayList<>();
        for (final FilmDAO filmDAO : filmsDAOs) {
            films.add(filmDAOtoFilm(filmDAO));
        }

        return films;
    }

    @Override
    public Film getFilmById(int filmId) {
        String sqlQuery = "SELECT film_id, name, description, release_date, duration, mpa_rating_id FROM films WHERE film_id=?";
        try {
            FilmDAO filmDAO = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilmDAO, filmId);
            return filmDAOtoFilm(filmDAO);
        } catch (DataAccessException e) {
            throw new FilmNotFoundException("Фильм с id " + filmId + " не найден.");
        }
    }

    @Override
    public void addUserLike(int filmId, int userId) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("film_to_likes")
                .usingGeneratedKeyColumns("like_id");

        Map<String, Object> map = new HashMap<>();
        map.put("film_id", filmId);
        map.put("user_id", userId);

        simpleJdbcInsert.execute(map);
    }

    @Override
    public void deleteUserLike(int filmId, int userId) {
        String sqlQuery = "DELETE FROM film_to_likes WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        return getFilms().stream()
                .sorted((o1, o2) -> o2.getUserLikes().size() - o1.getUserLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    private Map<String, Object> toMap(Film film) {
        Map<String, Object> values = new HashMap<>();
        values.put("name", film.getName());
        values.put("description", film.getDescription());
        values.put("release_date", film.getReleaseDate());
        values.put("duration", film.getDuration());
        values.put("mpa_rating_id", film.getMpa().getId());
        return values;
    }

    private FilmDAO mapRowToFilmDAO(ResultSet resultSet, int rowNum) throws SQLException {
        return FilmDAO.builder()
                .filmId(resultSet.getInt("film_id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .mpaRatingId(resultSet.getInt("mpa_rating_id"))
                .build();
    }

    @Builder
    @Getter
    private static final class FilmDAO {
        private final Integer filmId;
        private final String name;
        private final String description;
        private final LocalDate releaseDate;
        private final Integer duration;
        private final Integer mpaRatingId;
    }

    private Film filmDAOtoFilm(final FilmDAO filmDAO) {
        Film film = Film.builder()
                .id(filmDAO.getFilmId())
                .name(filmDAO.getName())
                .description(filmDAO.getDescription())
                .releaseDate(filmDAO.getReleaseDate())
                .duration(filmDAO.getDuration())
                .build();

        assignMpa(filmDAO, film);
        assignGenres(film);

        return film;
    }

    private void assignMpa(FilmDAO filmDAO, Film film) {
        Integer mpaRatingId = filmDAO.getMpaRatingId();
        if (mpaRatingId != null) {
            String sqlQuery = "SELECT type FROM mpa_rating WHERE mpa_rating_id=?";
            SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sqlQuery, mpaRatingId);
            String type = sqlRowSet.getString("type");
            Mpa mpa = new Mpa(mpaRatingId, type);
            film.setMpa(mpa);
        }
    }

    private void assignGenres(Film film) {
        String sqlQuery = "SELECT genre_id FROM film_to_genres WHERE film_id=?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sqlQuery, film.getId());
        List<Genre> genres = new ArrayList<>();
        while (sqlRowSet.next()) {
            int genreId = sqlRowSet.getInt("genre_id");
            String subSqlQuery = "SELECT name FROM genres WHERE genre_id=?";
            SqlRowSet subSqlRowSet = jdbcTemplate.queryForRowSet(subSqlQuery, genreId);
            String name = subSqlRowSet.getString("name");

            final Genre genre = new Genre(genreId, name);
            genres.add(genre);
        }

        film.setGenres(genres.stream()
                .sorted(Comparator.comparing(Genre::getId))
                .collect(Collectors.toList()));
    }
}
