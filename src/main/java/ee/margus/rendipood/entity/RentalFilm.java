package ee.margus.rendipood.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RentalFilm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Film film;
    private int initialDays;
    private int lateDays;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;
    private boolean returned;
}
