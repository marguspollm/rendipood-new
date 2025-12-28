import type { FilmType } from "./FilmType";

export type Film = {
  id?: number;
  title: string;
  type: FilmType;
  inStock: boolean;
};
