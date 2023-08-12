package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MpaDbStorageTest {

    private final MpaDbStorage mpaDbStorage;

    private final Map<Integer, String> expectedResults = Map.of(
            1, "G",
            2, "PG",
            3, "PG-13",
            4, "R",
            5, "NC-17"
    );

    @Test
    void getMpaByKnownIdShouldBeOk() {
        for (var entry : expectedResults.entrySet()) {
            Integer expectedId = entry.getKey();
            String expectedType = entry.getValue();

            Optional<Mpa> mpaOpt = mpaDbStorage.getMpaById(expectedId);
            assertTrue(mpaOpt.isPresent());

            Mpa mpa = mpaOpt.get();
            assertEquals(expectedId, mpa.getId());
            assertEquals(expectedType, mpa.getName());
        }
    }

    @Test
    void getMpaByUnknownIdShouldReturnEmptyOptional() {
        final int id = 7;
        Optional<Mpa> mpaOpt = mpaDbStorage.getMpaById(id);
        assertTrue(mpaOpt.isEmpty());
    }

    @Test
    void getAllMpasShouldBeOk() {
        List<Mpa> mpas = new ArrayList<>(mpaDbStorage.getAllMpas());
        for (final Mpa mpa : mpas) {
            final Integer expectedId = mpa.getId();
            final String expectedType = expectedResults.get(expectedId);

            assertEquals(expectedId, mpa.getId());
            assertEquals(expectedType, mpa.getName());
        }

        final List<Mpa> sorted = mpaDbStorage.getAllMpas().stream()
                .sorted(Comparator.comparingInt(Mpa::getId))
                .collect(Collectors.toList());
        assertEquals(sorted, mpas);
    }
}