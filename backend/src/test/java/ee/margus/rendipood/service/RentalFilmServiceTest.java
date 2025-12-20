package ee.margus.rendipood.service;

import ee.margus.rendipood.entity.Rental;
import ee.margus.rendipood.entity.RentalFilm;
import ee.margus.rendipood.repository.RentalFilmRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RentalFilmServiceTest {
    @Mock
    private RentalFilmRepository repository;

    @InjectMocks
    private RentalFilmService service;

    @Test
    void getRentedFilms() {
        List<RentalFilm> rentalFilms = new ArrayList<>();
        RentalFilm rentalFilm = new RentalFilm();
        rentalFilm.setReturned(false);
        rentalFilms.add(rentalFilm);

        when(repository.findByReturnedFalse()).thenReturn(rentalFilms);

        assertEquals(rentalFilms, service.getRentedFilms());
    }
}