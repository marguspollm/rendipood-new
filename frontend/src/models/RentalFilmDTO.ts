import type { Film } from "./Film";

export type RentalFilmWithDays = Film & {
  days: number;
};
