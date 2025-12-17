package ee.margus.rendipood.controller;

import ee.margus.rendipood.entity.Film;
import ee.margus.rendipood.entity.FilmType;
import ee.margus.rendipood.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FilmController {
    @Autowired
    private FilmService filmService;

    @GetMapping("films")
    public List<Film> filmsInStore(@RequestParam(required = false) Boolean inStock) {
        return filmService.filmsInStore(inStock);
    }

    @PostMapping("films")
    public List<Film> addFilm(@RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @PostMapping("films-multi")
    public List<Film> addFilms(@RequestBody List<Film> films) {
        return filmService.addFilms(films);
    }

    @DeleteMapping("films/{id}")
    public List<Film> deleteFilm(@PathVariable Long id) {
        return filmService.deleteFilm(id);
    }

    @PatchMapping("films-type")
    public List<Film> updateFilm(@RequestParam Long id, @RequestParam FilmType type) {
        return filmService.updateFilm(id, type);
    }

}
