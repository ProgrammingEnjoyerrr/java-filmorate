package ru.yandex.practicum.filmorate.storage.inmemory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.*;
import java.util.stream.Collectors;

@Qualifier("inMemoryMpaStorage")
@Component
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
        return idToMpa.values()
                .stream()
                .sorted(Comparator.comparingInt(Mpa::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Mpa> getMpaById(int id) {
        return Optional.ofNullable(idToMpa.get(id));
    }
}
