package ru.yandex.practicum.filmorate.storage.db;

import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.Collection;
import java.util.Optional;

public class MpaDbStorage implements MpaStorage {
    @Override
    public Collection<Mpa> getAllMpas() {
        return null;
    }

    @Override
    public Optional<Mpa> getMpaById(int id) {
        return Optional.empty();
    }
}
