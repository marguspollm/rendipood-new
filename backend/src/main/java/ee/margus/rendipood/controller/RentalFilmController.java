package ee.margus.rendipood.controller;

import ee.margus.rendipood.entity.RentalFilm;
import ee.margus.rendipood.service.RentalFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class RentalFilmController {
    @Autowired
    private RentalFilmService rentalFilmService;

    @GetMapping("rented-films")
    public List<RentalFilm> getRentedFilms() {
        return rentalFilmService.getRentedFilms();
    }
}
