package ee.margus.rendipood.service;

import ee.margus.rendipood.entity.RentalFilm;
import ee.margus.rendipood.repository.RentalFilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalFilmService {
    @Autowired
    private RentalFilmRepository rentalFilmRepository;


    public List<RentalFilm> getRentedFilms() {
        return rentalFilmRepository.findByReturnedFalse();
    }
}
