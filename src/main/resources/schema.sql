CREATE TABLE IF NOT EXISTS mpa_rating
(
    mpa_rating_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    type          VARCHAR(40)

);

CREATE TABLE IF NOT EXISTS genres
(
    genre_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name     VARCHAR(40) NOT NULL

);

CREATE TABLE IF NOT EXISTS films
(
    film_id       INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    description   VARCHAR(200) NOT NULL,
    release_date  TIMESTAMP    NOT NULL,
    duration      INTEGER      NOT NULL,
    mpa_rating_id INTEGER REFERENCES mpa_rating (mpa_rating_id)

);

CREATE TABLE IF NOT EXISTS users
(
    user_id  INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    login    VARCHAR(50) NOT NULL,
    name     VARCHAR(50),
    email    VARCHAR(50) NOT NULL,
    birthday DATE        NOT NULL

);

CREATE TABLE IF NOT EXISTS film_to_likes
(
    like_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_id INTEGER NOT NULL REFERENCES films (film_id),
    user_id INTEGER NOT NULL REFERENCES users (user_id)

);

CREATE TABLE IF NOT EXISTS film_to_genres
(
    id       INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_id  INTEGER NOT NULL REFERENCES films (film_id),
    genre_id INTEGER NOT NULL REFERENCES genres (genre_id)

);

CREATE TABLE IF NOT EXISTS user_friends
(
    id                INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id           INTEGER NOT NULL REFERENCES users (user_id),
    friend_id         INTEGER NOT NULL REFERENCES users (user_id),
    friendship_status BOOLEAN NOT NULL
);