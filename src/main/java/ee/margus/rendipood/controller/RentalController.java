package ee.margus.rendipood.controller;

import ee.margus.rendipood.dto.RentalFilmDTO;
import ee.margus.rendipood.entity.Film;
import ee.margus.rendipood.entity.Rental;
import ee.margus.rendipood.entity.RentalFilm;
import ee.margus.rendipood.repository.FilmRepository;
import ee.margus.rendipood.repository.RentalFilmRepository;
import ee.margus.rendipood.repository.RentalRepository;
import ee.margus.rendipood.util.FeeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RentalController {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private RentalFilmRepository rentalFilmRepository;

    @PostMapping("start-rental")
    public double startRental(@RequestBody List<RentalFilmDTO> rentalFilms){
        double sum = 0;

        Rental rental = new Rental();

        List<Film> films = new ArrayList<>();
        List<RentalFilm> rentalFilmsList = new ArrayList<>();

        for(RentalFilmDTO rentalFilmDTO: rentalFilms){
            Film film = filmRepository.findById(rentalFilmDTO.getFilmId()).orElseThrow();
            film.setInStock(false);
            films.add(film);

            RentalFilm rentalFilm = new RentalFilm();
            rentalFilm.setFilm(film);
            rentalFilm.setInitialDays(rentalFilmDTO.getDays());
            rentalFilm.setLateDays(0);
            rentalFilmsList.add(rentalFilm);
            rentalFilm.setRental(rental);
            sum += FeeCalculator.initialFee(film.getType(), rentalFilmDTO.getDays());
        }
        filmRepository.saveAll(films);

        rental.setInitialFee(sum);
        rental.setLateFee(0);
        rental.setRentalFilms(rentalFilmsList); //RentalFilm entiti .save abil andmebaasi
            //tänu Cascade.ALL reale entitis

        rentalRepository.save(rental);
        return sum;
    }

    //TODO (kui ei õnnestu pole hullu) --> dto(filmId, tegelikud rendipäevad)
    @PostMapping("end-rental")
    public double endRental(@RequestBody List<RentalFilmDTO> rentalFilmDTO){
        double sum = 0;

        List<Film> films = new ArrayList<>();
        List<RentalFilm> rentalFilmsList = new ArrayList<>();
        List<Rental> rentals = new ArrayList<>();

        for (RentalFilmDTO dto: rentalFilmDTO){
            Film film = filmRepository.findById(dto.getFilmId()).orElseThrow();
            film.setInStock(true);
            films.add(film);

            RentalFilm rentalFilm = rentalFilmRepository.findByFilm_IdAndReturnedFalse(dto.getFilmId());
            int lateDays = Math.max(0, dto.getDays() - rentalFilm.getInitialDays());
            rentalFilm.setLateDays(lateDays);
            rentalFilm.setReturned(true);
            rentalFilmsList.add(rentalFilm);

            Rental rental = rentalRepository.findById(rentalFilm.getRental().getId()).orElseThrow();
            sum += rental.getLateFee() + FeeCalculator.lateFee(film.getType(), lateDays);
            rental.setLateFee(sum);
            rentals.add(rental);
        }

        filmRepository.saveAll(films);
        rentalRepository.saveAll(rentals);
        rentalFilmRepository.saveAll(rentalFilmsList);
        return sum;
    }
}
