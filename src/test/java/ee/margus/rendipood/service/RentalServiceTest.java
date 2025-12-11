package ee.margus.rendipood.service;

import ee.margus.rendipood.dto.RentalFilmDTO;
import ee.margus.rendipood.entity.Film;
import ee.margus.rendipood.entity.FilmType;
import ee.margus.rendipood.repository.FilmRepository;
import ee.margus.rendipood.repository.RentalFilmRepository;
import ee.margus.rendipood.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ee.margus.rendipood.entity.FilmType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RentalServiceTest {
    //given_when_then
    //eeltingimused_misKäivitatakse_misOnTulemus
    @Mock
    private FilmRepository filmRepository;
    @Mock
    private RentalRepository rentalRepository;
    @Mock
    private RentalFilmRepository rentalFilmRepository;

    @InjectMocks
    private RentalService rentalService;

    @BeforeEach
    void setUp(){

//        mockSaveFilmToDb(2L, "Matrix 2", true, NEW);
//        mockSaveFilmToDb(3L, "Matrix 3", true, REGULAR);
//
//        mockSaveFilmToDb(5L, "Spiderman 2", true, OLD);
//        mockSaveFilmToDb(6L, "Spiderman 3", true, REGULAR);
    }

    private void mockSaveFilmToDb(Long id, String name, boolean inStock, FilmType type) {
        Film film = new Film();
        film.setId(id);
        film.setTitle(name);
        film.setInStock(inStock);
        film.setType(type);
        when(filmRepository.findById(id)).thenReturn(Optional.of(film));
    }

    private static RentalFilmDTO getRentalFilmDTO(Long id, int days) {
        RentalFilmDTO rentalFilmDTO = new RentalFilmDTO();
        rentalFilmDTO.setFilmId(id);
        rentalFilmDTO.setDays(days);
        return rentalFilmDTO;
    }

    @Test
    void givenWhenFilmIsNotInStock_whenRentalIsStarted_thenThrowException(){
        mockSaveFilmToDb(1L, "Matrix", false, NEW);

        RentalFilmDTO rentalFilmDTO = getRentalFilmDTO(1L, 1);
        List<RentalFilmDTO> rentalFilmDTOList = new ArrayList<>();
        rentalFilmDTOList.add(rentalFilmDTO);

        String message = assertThrows(RuntimeException.class, () -> rentalService.startRental(rentalFilmDTOList)).getMessage();
        assertEquals("Film is out of stock!", message);
    }

    @Test
    void givenWhenFilmIsOldAndRentedForFiveDays_whenRentalIsStarted_thenInitialFeeIs3(){
        mockSaveFilmToDb(4L, "Spiderman", true, OLD);

        RentalFilmDTO rentalFilmDTO = getRentalFilmDTO(4L, 5);
        List<RentalFilmDTO> rentalFilmDTOList = new ArrayList<>();
        rentalFilmDTOList.add(rentalFilmDTO);

        double sum = rentalService.startRental(rentalFilmDTOList);
        assertEquals(3, sum);
    }

    @Test
    void startRental() {

    }

    @Test
    void endRental() {

    }
}