package ee.margus.rendipood.util;

import ee.margus.rendipood.entity.FilmType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class FeeCalculatorTest {

    @Test
    void givenFilmTypeIsNewAndDaysIsTwo_whenCalculatingInitialFee_thenFeeIsEight(){
        assertEquals(8,FeeCalculator.initialFee(FilmType.NEW, 2));
    }
    @Test
    void givenFilmTypeIsRegularAndDaysIsThree_whenCalculatingInitialFee_thenFeeIsThree(){
        assertEquals(3,FeeCalculator.initialFee(FilmType.REGULAR, 3));
    }
    @Test
    void givenFilmTypeIsOldAndDaysIsFive_whenCalculatingInitialFee_thenFeeIs3(){
        assertEquals(3,FeeCalculator.initialFee(FilmType.OLD, 5));
    }
    @Test
    void givenFilmTypeIsRegularAndDaysIsFour_whenCalculatingInitialFee_thenFeeIsSix(){
        assertEquals(6,FeeCalculator.initialFee(FilmType.REGULAR, 4));
    }
    @Test
    void givenFilmTypeIsOldAndDaysIsSix_whenCalculatingInitialFee_thenFeeIsSix(){
        assertEquals(6,FeeCalculator.initialFee(FilmType.OLD, 6));
    }

    @Test
    void givenFilmTypeIsOldAndDaysIsZero_whenCalculatingInitialFee_thenThrowException(){
        String message = assertThrows(RuntimeException.class, () -> FeeCalculator.initialFee(FilmType.OLD, 0)).getMessage();
        assertEquals("Days cannot be zero!", message);
    }

    @Test
    void givenFilmTypeIsNewAndDaysOverIsTwo_whenCalculatingLateFee_thenFeeIsEight(){
        assertEquals(8,FeeCalculator.lateFee(FilmType.NEW, 2));
    }
    @Test
    void givenFilmTypeIsRegularAndDaysOverIsTwo_whenCalculatingLateFee_thenFeeIsSix(){
        assertEquals(6,FeeCalculator.lateFee(FilmType.REGULAR, 2));
    }
    @Test
    void givenFilmTypeIsOldAndDaysOverIsTwo_whenCalculatingLateFee_thenFeeIsSix(){
        assertEquals(6,FeeCalculator.lateFee(FilmType.OLD, 2));
    }

    @Test
    void givenFilmTypeIsOldAndDaysIsZero_whenCalculatingLateFee_theFeeIsZero(){
        assertEquals(0, FeeCalculator.lateFee(FilmType.OLD, 0));
    }
}