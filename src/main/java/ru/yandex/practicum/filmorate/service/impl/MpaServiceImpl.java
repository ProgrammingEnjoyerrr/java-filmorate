package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
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
public class MpaServiceImpl implements MpaService {

    @Qualifier("inMemoryMpaStorage")
    private final MpaStorage mpaStorage;

    @Override
    public Collection<Mpa> getAllMpas() {
        return mpaStorage.getAllMpas();
    }

    @Override
    public Mpa getMpaById(int id) {
        Optional<Mpa> mpaOpt = mpaStorage.getMpaById(id);

        if (mpaOpt.isEmpty()) {
            throw new MpaNotFoundException("Попытка получить MPA с несуществующим id " + id);
        }

        return mpaOpt.get();
    }
}
