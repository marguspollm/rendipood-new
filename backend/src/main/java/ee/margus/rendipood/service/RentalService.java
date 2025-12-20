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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private RentalFilmRepository rentalFilmRepository;

    public double startRental(List<RentalFilmDTO> rentalFilms) {
        double sum = 0;

        Rental rental = new Rental();

        List<Film> films = new ArrayList<>();
        List<RentalFilm> rentalFilmsList = new ArrayList<>();

        for (RentalFilmDTO rentalFilmDTO : rentalFilms) {
            Film film = filmRepository.findById(rentalFilmDTO.getFilmId()).orElseThrow(() -> new RuntimeException("Film is not in database!"));
            if (!film.getInStock()) {
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

        List<Film> filmsToUpdate = new ArrayList<>();
        List<RentalFilm> rentalFilmsToUpdate = new ArrayList<>();
        Map<Long, Rental> rentalsToUpdate = new HashMap<>();
        Map<Long, Double> rentalLateFees = new HashMap<>();

        for (RentalFilmDTO dto : rentalFilmDTO) {
            Film film = filmRepository.findById(dto.getFilmId())
                    .orElseThrow(() -> new RuntimeException("Film is not in database!"));
            film.setInStock(true);
            filmsToUpdate.add(film);

            RentalFilm rentalFilm = getRentalFilm(dto);
            rentalFilmsToUpdate.add(rentalFilm);

            Rental rental = rentalRepository.findById(rentalFilm.getRental().getId())
                    .orElseThrow(() -> new RuntimeException("Rental doesn't exist!"));

            sum += FeeCalculator.lateFee(film.getType(), rentalFilm.getLateDays());
            rentalLateFees.merge(rental.getId(), sum, Double::sum);
            rentalsToUpdate.put(rental.getId(), rental);
        }

        for (Rental rental : rentalsToUpdate.values()) {
            double totalFee = rental.getLateFee() + rentalLateFees.get(rental.getId());
            rental.setLateFee(totalFee);
        }

        filmRepository.saveAll(filmsToUpdate);
        rentalRepository.saveAll(rentalsToUpdate.values());
        rentalFilmRepository.saveAll(rentalFilmsToUpdate);
        return sum;
    }

    private RentalFilm getRentalFilm(RentalFilmDTO dto) {
        RentalFilm rentalFilm = rentalFilmRepository.findByFilm_IdAndReturnedFalse(dto.getFilmId())
                .orElseThrow(() -> new RuntimeException("Film rental doesn't exist!"));
        int lateDays = Math.max(0, dto.getDays() - rentalFilm.getInitialDays());
        rentalFilm.setLateDays(lateDays);
        rentalFilm.setReturned(true);
        return rentalFilm;
    }

    public List<Rental> getRentals() {
        return rentalRepository.findAll();
    }
}
