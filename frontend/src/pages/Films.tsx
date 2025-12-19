import { useState, useEffect } from "react";
import type { Film } from "../models/Film";

function Films() {
  const [films, setFilms] = useState<Film[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/films")
      .then((res) => res.json())
      .then((json) => setFilms(json));
  }, []);
  return (
    <>
      {films.map((film) => (
        <div key={film.id}>
          <div>{film.title}</div>
          <div>{film.filmType}</div>
        </div>
      ))}
    </>
  );
}

export default Films;
