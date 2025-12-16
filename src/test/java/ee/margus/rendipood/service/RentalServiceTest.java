package ee.margus.rendipood.service;

import ee.margus.rendipood.dto.RentalFilmDTO;
import ee.margus.rendipood.entity.Film;
import ee.margus.rendipood.entity.Rental;
import ee.margus.rendipood.entity.RentalFilm;
import ee.margus.rendipood.repository.FilmRepository;
import ee.margus.rendipood.repository.RentalFilmRepository;
import ee.margus.rendipood.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ee.margus.rendipood.entity.FilmType.NEW;
import static ee.margus.rendipood.entity.FilmType.OLD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private void mockSaveFilmToDb(Film film) {
        film.setId(film.getId());
        film.setTitle(film.getTitle());
        film.setInStock(film.getInStock());
        film.setType(film.getType());
        when(filmRepository.findById(film.getId())).thenReturn(Optional.of(film));
    }

    private static RentalFilmDTO getRentalFilmDTO(Long id, int days) {
        RentalFilmDTO rentalFilmDTO = new RentalFilmDTO();
        rentalFilmDTO.setFilmId(id);
        rentalFilmDTO.setDays(days);
        return rentalFilmDTO;
    }

    //start-rental
    @Test
    void givenWhenFilmIsNotInStock_whenRentalIsStarted_thenThrowException(){
        Film film = new Film(1L, "Matrix","", NEW, false);
        mockSaveFilmToDb(film);

        RentalFilmDTO rentalFilmDTO = getRentalFilmDTO(1L, 1);
        List<RentalFilmDTO> rentalFilmDTOList = new ArrayList<>();
        rentalFilmDTOList.add(rentalFilmDTO);

        String message = assertThrows(RuntimeException.class, () -> rentalService.startRental(rentalFilmDTOList)).getMessage();
        assertEquals("Film is out of stock!", message);
    }

    @Test
    void givenWhenFilmIsOldAndRentedForFiveDays_whenRentalIsStarted_thenInitialFeeIsThree(){
        Film film = new Film(1L, "Matrix","", OLD, true);
        mockSaveFilmToDb(film);

        RentalFilmDTO rentalFilmDTO = getRentalFilmDTO(1L, 5);
        List<RentalFilmDTO> rentalFilmDTOList = new ArrayList<>();
        rentalFilmDTOList.add(rentalFilmDTO);

        double sum = rentalService.startRental(rentalFilmDTOList);
        assertEquals(3, sum);
    }

    @Test
    void givenWhenFilmIsNotInDb_whenRentalIsStarted_thenThrowException(){
        RentalFilmDTO rentalFilmDTO = getRentalFilmDTO(1L, 1);
        List<RentalFilmDTO> rentalFilmDTOList = new ArrayList<>();
        rentalFilmDTOList.add(rentalFilmDTO);

        String message = assertThrows(RuntimeException.class, () -> rentalService.startRental(rentalFilmDTOList)).getMessage();
        assertEquals("Film is not in database!", message);
    }

    @Test
    void givenArrayOfTwoNewFilmsAndRentedForTwoDays_whenRentalIsStarted_thenInitialFeeIs16(){
        Film film1 = new Film(1L, "Matrix","", NEW, true);
        Film film2 = new Film(2L, "Matrix 2","", NEW, true);
        mockSaveFilmToDb(film1);
        mockSaveFilmToDb(film2);

        List<RentalFilmDTO> rentalFilmDTOList = new ArrayList<>();
        for(int i = 1; i <= 2; i++){
            RentalFilmDTO rentalFilmDTO = getRentalFilmDTO((long) i, 2);
            rentalFilmDTOList.add(rentalFilmDTO);
        }

        double sum = rentalService.startRental(rentalFilmDTOList);
        assertEquals(16, sum);
    }

    //end-rental
    @Test
    void givenWhenFilmIsNotInDb_whenRentalIsEnded_thenThrowException(){
        RentalFilmDTO rentalFilmDTO = getRentalFilmDTO(1L, 1);
        List<RentalFilmDTO> rentalFilmDTOList = new ArrayList<>();
        rentalFilmDTOList.add(rentalFilmDTO);

        String message = assertThrows(RuntimeException.class, () -> rentalService.endRental(rentalFilmDTOList)).getMessage();
        assertEquals("Film is not in database!", message);
    }

    @Test
    void givenWhenRentalIsNotInDb_whenRentalIsEnded_thenThrowException(){
        Film film = new Film(1L, "Matrix","", OLD, false);
        mockSaveFilmToDb(film);

        RentalFilmDTO rentalFilmDTO = getRentalFilmDTO(1L, 1);
        List<RentalFilmDTO> rentalFilmDTOList = new ArrayList<>();
        rentalFilmDTOList.add(rentalFilmDTO);

        String message = assertThrows(RuntimeException.class, () -> rentalService.endRental(rentalFilmDTOList)).getMessage();
        assertEquals("Film rental doesn't exist!", message);
    }

    @Test
    void givenWhenFilmIsOldAndRentedForTwoDaysAndReturnedOnTime_whenRentalIsEnded_thenLateFeeIsZero(){
        Film film = new Film(1L, "Spiderman", "", NEW, false);
        mockSaveFilmToDb(film);
        Rental rental = new Rental();
        rental.setId(1L);
        rental.setInitialFee(8);
        rental.setLateFee(0);

        RentalFilm rentalFilm = new RentalFilm(1L, film, 2, 0, rental, false);

        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));
        when(rentalFilmRepository.findByFilm_IdAndReturnedFalse(1L)).thenReturn(Optional.of(rentalFilm));

        RentalFilmDTO rentalFilmDTO = getRentalFilmDTO(1L, 2);
        List<RentalFilmDTO> rentalFilmDTOList = new ArrayList<>();
        rentalFilmDTOList.add(rentalFilmDTO);

        double sum = rentalService.endRental(rentalFilmDTOList);
        assertEquals(0, sum);
    }

    @Test
    void givenWhenFilmIsOldAndRentedForTwoDaysAndReturnedOneDayLate_whenRentalIsEnded_thenLateFeeIsThree(){
        Film film = new Film(1L, "Spiderman", "", OLD, false);
        mockSaveFilmToDb(film);
        Rental rental = new Rental();
        rental.setId(1L);
        rental.setInitialFee(8);
        rental.setLateFee(0);

        RentalFilm rentalFilm = new RentalFilm(1L, film, 2, 0, rental, false);

        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));
        when(rentalFilmRepository.findByFilm_IdAndReturnedFalse(1L)).thenReturn(Optional.of(rentalFilm));

        RentalFilmDTO rentalFilmDTO = getRentalFilmDTO(1L, 3);
        List<RentalFilmDTO> rentalFilmDTOList = new ArrayList<>();
        rentalFilmDTOList.add(rentalFilmDTO);

        double sum = rentalService.endRental(rentalFilmDTOList);
        assertEquals(3, sum);
    }

    @Test
    void givenWhenTwoFilmsAreNewAndRentedForTwoDaysAndReturnedOneDayLate_whenRentalIsEnded_thenLateFeeIsEight(){
        Film film1 = new Film(1L, "Spiderman", "", NEW, false);
        Film film2 = new Film(2L, "Spiderman", "", NEW, false);
        mockSaveFilmToDb(film1);
        mockSaveFilmToDb(film2);
        Rental rental = new Rental();
        rental.setId(1L);
        rental.setInitialFee(8);
        rental.setLateFee(0);

        RentalFilm rentalFilm1 = new RentalFilm(1L, film1, 2, 0, rental, false);
        RentalFilm rentalFilm2 = new RentalFilm(2L, film2, 2, 0, rental, false);

        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));
        when(rentalFilmRepository.findByFilm_IdAndReturnedFalse(1L)).thenReturn(Optional.of(rentalFilm1));
        when(rentalFilmRepository.findByFilm_IdAndReturnedFalse(2L)).thenReturn(Optional.of(rentalFilm2));

        List<RentalFilmDTO> rentalFilmDTOList = new ArrayList<>();
        for(int i = 1; i <= 2; i++){
            RentalFilmDTO rentalFilmDTO = getRentalFilmDTO((long) i, 3);
            rentalFilmDTOList.add(rentalFilmDTO);
        }

        double sum = rentalService.endRental(rentalFilmDTOList);
        assertEquals(8, sum);
    }
}