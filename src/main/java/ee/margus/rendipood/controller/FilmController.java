package ee.margus.rendipood.controller;

import ee.margus.rendipood.entity.Film;
import ee.margus.rendipood.entity.FilmType;
import ee.margus.rendipood.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FilmController {
    @Autowired
    private FilmRepository filmRepository;

    @GetMapping("films")
    public List<Film> filmsInStore(@RequestParam(required = false) Boolean inStock){
        if(inStock == null) return filmRepository.findAllByOrderByIdAsc();
        return filmRepository.findByInStockOrderByIdAsc(inStock);
    }

    @PostMapping("films")
    public List<Film> addFilm(@RequestBody Film film){
        if(film.getId() != null) throw new RuntimeException("Can't add a film with an existing id!");
        film.setInStock(true);
        filmRepository.save(film);
        return filmRepository.findAllByOrderByIdAsc();
    }

    @PostMapping("films-multi")
    public List<Film> addFilms(@RequestBody List<Film> films) {
        for (Film f : films) {
            if (f.getId() != null) {
                throw new RuntimeException("Can't add a film without an id");
            }
            f.setInStock(true);
            filmRepository.save(f);
        }
        return filmRepository.findAllByOrderByIdAsc();
    }

    @DeleteMapping("films/{id}")
    public List<Film> deleteFilm(@PathVariable Long id){
        filmRepository.deleteById(id);
        return filmRepository.findAllByOrderByIdAsc();
    }

    @PatchMapping("films-type")
    public List<Film> updateFilm(@RequestParam Long id, @RequestParam FilmType type){
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film with id"+ id +" not found"));
        film.setType(type);
        filmRepository.save(film);
        return filmRepository.findAllByOrderByIdAsc();
    }

}
