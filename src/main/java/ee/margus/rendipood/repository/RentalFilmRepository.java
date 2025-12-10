package ee.margus.rendipood.repository;

import ee.margus.rendipood.entity.RentalFilm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalFilmRepository extends JpaRepository<RentalFilm, Long> {

    RentalFilm findByFilm_Id(Long id);

    RentalFilm findByFilm_IdAndIsReturnedFalse(Long id);
}
