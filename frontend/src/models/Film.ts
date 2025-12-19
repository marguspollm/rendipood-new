import type { FilmType } from "./FilmType";

export type Film = {
  id: number;
  title: string;
  filmType: FilmType;
  insStock: boolean;
};
