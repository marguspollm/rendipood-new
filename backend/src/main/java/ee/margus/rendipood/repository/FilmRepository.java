package ee.margus.rendipood.repository;

import ee.margus.rendipood.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
    //List<Film> findByInStock(boolean inStock);

    List<Film> findByInStockOrderByIdAsc(Boolean inStock);

    List<Film> findAllByOrderByIdAsc();
}
