import type { RentalFilm } from "./RentalFilm";

export type Rental = {
  id: number;
  initialFee: number;
  lateFee: number;
  rentalFilms: RentalFilm[];
};
