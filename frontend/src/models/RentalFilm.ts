import type { Film } from "./Film";
import type { Rental } from "./Rental";

export type RentalFilm = {
  id: number;
  film: Film;
  initialDays: number;
  lateDays: number;
  rental: Rental;
  returned: boolean;
};
