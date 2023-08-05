package ru.yandex.practicum.filmorate.storage.inmemory;

import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class InMemoryMpaStorage implements MpaStorage {
    private final Map<Integer, Mpa> idToMpa = Map.of(
            1, new Mpa(1, "G"),
            2, new Mpa(2, "PG"),
            3, new Mpa(3, "PG-13"),
            4, new Mpa(4, "R"),
            5, new Mpa(5, "NC-17")
    );

    @Override
    public Collection<Mpa> getAllMpas() {
        return new ArrayList<>(idToMpa.values());
    }

    @Override
    public Optional<Mpa> getMpaById(int id) {
        return Optional.ofNullable(idToMpa.get(id));
    }
}
