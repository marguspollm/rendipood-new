package ee.margus.rendipood.repository;

import ee.margus.rendipood.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
