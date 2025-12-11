package ee.margus.rendipood.service;

import ee.margus.rendipood.dto.RentalFilmDTO;
import ee.margus.rendipood.entity.Film;
import ee.margus.rendipood.entity.Rental;
import ee.margus.rendipood.entity.RentalFilm;
import ee.margus.rendipood.repository.FilmRepository;
import ee.margus.rendipood.repository.RentalFilmRepository;
import ee.margus.rendipood.repository.RentalRepository;
import ee.margus.rendipood.util.FeeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private RentalFilmRepository rentalFilmRepository;

    public double startRental(List<RentalFilmDTO> rentalFilms){
        double sum = 0;

        Rental rental = new Rental();

        List<Film> films = new ArrayList<>();
        List<RentalFilm> rentalFilmsList = new ArrayList<>();

        for(RentalFilmDTO rentalFilmDTO: rentalFilms){
            Film film = filmRepository.findById(rentalFilmDTO.getFilmId()).orElseThrow();
            if(!film.getInStock()){
                throw new RuntimeException("Film is out of stock!");
            }
            film.setInStock(false);
            films.add(film);

            RentalFilm rentalFilm = getRentalFilm(rentalFilmDTO, film, rental);
            rentalFilmsList.add(rentalFilm);

            sum += FeeCalculator.initialFee(film.getType(), rentalFilmDTO.getDays());
        }
        filmRepository.saveAll(films);

        saveRental(rental, sum, rentalFilmsList);
        return sum;
    }

    private void saveRental(Rental rental, double sum, List<RentalFilm> rentalFilmsList) {
        rental.setInitialFee(sum);
        rental.setLateFee(0);
        rental.setRentalFilms(rentalFilmsList);
        //RentalFilm entiti .save abil andmebaasi
        //tänu Cascade.ALL reale entitis

        rentalRepository.save(rental);
    }

    private RentalFilm getRentalFilm(RentalFilmDTO rentalFilmDTO, Film film, Rental rental) {
        RentalFilm rentalFilm = new RentalFilm();
        rentalFilm.setFilm(film);
        rentalFilm.setInitialDays(rentalFilmDTO.getDays());
        rentalFilm.setLateDays(0);
        rentalFilm.setRental(rental);
        return rentalFilm;
    }

    public double endRental(List<RentalFilmDTO> rentalFilmDTO) {
        double sum = 0;

        List<Film> films = new ArrayList<>();
        List<RentalFilm> rentalFilmsList = new ArrayList<>();
        List<Rental> rentals = new ArrayList<>();

        for (RentalFilmDTO dto: rentalFilmDTO){
            Film film = filmRepository.findById(dto.getFilmId()).orElseThrow();
            film.setInStock(true);
            films.add(film);

            RentalFilm rentalFilm = getRentalFilm(dto);
            rentalFilmsList.add(rentalFilm);

            Rental rental = rentalRepository.findById(rentalFilm.getRental().getId()).orElseThrow();
            sum += rental.getLateFee() + FeeCalculator.lateFee(film.getType(), rentalFilm.getLateDays());
            rental.setLateFee(sum);
            rentals.add(rental);
        }

        filmRepository.saveAll(films);
        rentalRepository.saveAll(rentals);
        rentalFilmRepository.saveAll(rentalFilmsList);
        return sum;
    }

    private RentalFilm getRentalFilm(RentalFilmDTO dto) {
        RentalFilm rentalFilm = rentalFilmRepository.findByFilm_IdAndReturnedFalse(dto.getFilmId());
        int lateDays = Math.max(0, dto.getDays() - rentalFilm.getInitialDays());
        rentalFilm.setLateDays(lateDays);
        rentalFilm.setReturned(true);
        return rentalFilm;
    }
}
