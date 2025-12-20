import type { Film } from "./Film";

export type RentalFilmDTO = Film & {
  days: number;
};
