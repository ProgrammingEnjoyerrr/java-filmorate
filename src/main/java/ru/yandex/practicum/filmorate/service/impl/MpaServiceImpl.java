package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MpaServiceImpl implements MpaService {

    @Qualifier("mpaDbStorage")
    private final MpaStorage mpaStorage;

    @Override
    public Collection<Mpa> getAllMpas() {
        log.info("searching all MPAs");
        return mpaStorage.getAllMpas();
    }

    @Override
    public Mpa getMpaById(int id) {
        log.info("searching MPA with id {}", id);
        Optional<Mpa> mpaOpt = mpaStorage.getMpaById(id);

        if (mpaOpt.isEmpty()) {
            log.error("failed to find MPA with id {}", id);
            throw new MpaNotFoundException("Попытка получить MPA с несуществующим id " + id);
        }

        final Mpa mpa = mpaOpt.get();
        log.info("found MPA {}", mpa);
        return mpa;
    }
}
