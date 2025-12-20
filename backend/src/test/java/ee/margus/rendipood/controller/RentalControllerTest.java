package ee.margus.rendipood.controller;

import ee.margus.rendipood.dto.RentalFilmDTO;
import ee.margus.rendipood.service.RentalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RentalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RentalService rentalService;

    @Test
    void getRentals() throws Exception {
        mockMvc.perform(get("/rentals"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void startRental() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RentalFilmDTO rentalFilmDTO = new RentalFilmDTO();
        mockMvc.perform(post("/start-rental")
                        .content(objectMapper.writeValueAsString(List.of(rentalFilmDTO)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void endRental() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RentalFilmDTO rentalFilmDTO = new RentalFilmDTO();
        rentalFilmDTO.setDays(1);
        rentalFilmDTO.setFilmId(1L);
        mockMvc.perform(post("/end-rental")
                        .content(objectMapper.writeValueAsString(List.of(rentalFilmDTO)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}