package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
@Slf4j
@RequiredArgsConstructor
public class MpaController {
    private final MpaService mpaService;

    @GetMapping
    public Collection<Mpa> getAllMpas() {
        log.debug("got request GET /mpa");

        return mpaService.getAllMpas();
    }

    @GetMapping(value = "/{id}")
    public Mpa getMpaById(@PathVariable("id") int mpaId) {
        log.debug("got request GET /mpa/{}", mpaId);

        return mpaService.getMpaById(mpaId);
    }
}
