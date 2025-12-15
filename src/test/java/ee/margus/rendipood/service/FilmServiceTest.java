package ee.margus.rendipood.service;

import ee.margus.rendipood.entity.Film;
import ee.margus.rendipood.repository.FilmRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ee.margus.rendipood.entity.FilmType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmService filmService;

    @Test
    void givenInStockIsNull_whenGettingFilmsInStore_thenReturnAllInStockFilms() {
        List<Film> films = new ArrayList<>();
        Film film1 = new Film(1L, "Matrix", "", NEW, true);
        Film film2 = new Film(2L, "Matrix 2", "", REGULAR, true);
        films.add(film1);
        films.add(film2);

        when(filmRepository.findAllByOrderByIdAsc()).thenReturn(films);

        assertEquals(films, filmService.filmsInStore(null));
    }

    @Test
    void givenInStockIsTrue_whenGettingFilmsInStore_thenReturnAllInStockFilms() {
        List<Film> films = new ArrayList<>();
        Film film1 = new Film(1L, "Matrix", "", NEW, true);
        Film film2 = new Film(2L, "Matrix 2", "", REGULAR, true);
        films.add(film1);
        films.add(film2);

        when(filmRepository.findByInStockOrderByIdAsc(true)).thenReturn(films);

        assertEquals(films, filmService.filmsInStore(true));
    }

    @Test
    void givenInStockIsFalse_whenGettingFilmsInStore_thenReturnAllInStockFilms() {
        List<Film> films = new ArrayList<>();
        Film film = new Film(1L, "Matrix", "", NEW, false);
        films.add(film);

        when(filmRepository.findByInStockOrderByIdAsc(false)).thenReturn(films);

        assertEquals(films, filmService.filmsInStore(false));
    }

    @Test
    void givenFilmWithId_whenAddFilm_thenThrowException(){
        Film film = new Film(1L, "Matrix", "", NEW, false);

        String message = assertThrows(RuntimeException.class, () -> filmService.addFilm(film)).getMessage();
        assertEquals("Can't add a film with an existing id!", message);
    }

    @Test
    void givenCorrectFilm_whenAddFilm_thenSaveFilmAndReturnAllFilms(){
        List<Film> films = new ArrayList<>();
        Film film = new Film(null, "Matrix", "", NEW, null);
        films.add(film);

        when(filmRepository.findAllByOrderByIdAsc()).thenReturn(films);

        assertEquals(films, filmService.addFilm(film));
        assertTrue(film.getInStock());
    }

    @Test
    void givenListOfFilmsWithOneId_whenAddFilms_thenThrowException(){
        Film film = new Film(1L, "Matrix", "", NEW, null);

        String message = assertThrows(RuntimeException.class, () -> filmService.addFilm(film)).getMessage();
        assertEquals("Can't add a film with an existing id!", message);
    }

    @Test
    void givenListOfCorrectFilms_whenAddFilms_thenSaveFilmAndReturnAllFilms(){
        List<Film> films = new ArrayList<>();
        Film film1 = new Film(null, "Matrix", "", NEW, null);
        Film film2 = new Film(null, "Matrix 2", "", NEW, null);
        films.add(film1);
        films.add(film2);

        when(filmRepository.findAllByOrderByIdAsc()).thenReturn(films);

        assertEquals(films, filmService.addFilms(films));
        assertTrue(film1.getInStock());
        assertTrue(film2.getInStock());
    }

    @Test
    void givenFilmId_whenDeleteFilm_thenDeleteFilmAndReturnAllFilms(){
        List<Film> films = new ArrayList<>();
        Film film = new Film(1L, "Matrix", "", NEW, true);
        films.add(film);

        when(filmRepository.findAllByOrderByIdAsc()).thenReturn(films);

        assertEquals(films, filmService.deleteFilm(1L));
    }

    @Test
    void givenMissingFilmIdAndType_whenUpdateFilmAndNotFound_thenThrowException(){
        when(filmRepository.findById(1L)).thenReturn(Optional.empty());

        String message = assertThrows(RuntimeException.class, () -> filmService.updateFilm(1L, OLD)).getMessage();
        assertEquals("Film with id 1 not found", message);
    }

    @Test
    void givenFilmIdAndType_whenUpdateFilm_thenUpdateFilmTypeAndReturnsALlFilms(){
        List<Film> films = new ArrayList<>();
        Film film = new Film(1L, "Matrix", "", NEW, true);
        films.add(film);

        when(filmRepository.findAllByOrderByIdAsc()).thenReturn(films);
        when(filmRepository.findById(1L)).thenReturn(Optional.of(film));

        assertEquals(films, filmService.updateFilm(1L, OLD));
        assertEquals(OLD, film.getType());
    }
}