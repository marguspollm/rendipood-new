package ee.margus.rendipood.repository;

import ee.margus.rendipood.entity.RentalFilm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalFilmRepository extends JpaRepository<RentalFilm, Long> {

    RentalFilm findByFilm_Id(Long id);

    Optional<RentalFilm> findByFilm_IdAndReturnedFalse(Long id);
}
