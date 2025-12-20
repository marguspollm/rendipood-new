package ee.margus.rendipood.controller;

import ee.margus.rendipood.dto.RentalFilmDTO;
import ee.margus.rendipood.entity.Rental;
import ee.margus.rendipood.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class RentalController {
    @Autowired
    private RentalService rentalService;
//TODO validations
    @GetMapping("rentals")
    public List<Rental> getRentals(){
        return rentalService.getRentals();
    }

    @PostMapping("start-rental")
    public double startRental(@RequestBody List<RentalFilmDTO> rentalFilms) {
        return rentalService.startRental(rentalFilms);
    }

    @PostMapping("end-rental")
    public double endRental(@RequestBody List<RentalFilmDTO> rentalFilmDTO) {
        return rentalService.endRental(rentalFilmDTO);
    }


}
