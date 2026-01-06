import { useState, useEffect, useContext } from "react";
import type { Film } from "../models/Film";
import { apiFetch } from "../services/api";
import {
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  Skeleton,
  Typography,
} from "@mui/material";
import { toast, ToastContainer } from "react-toastify";
import { useTranslation } from "react-i18next";
import type { FilmWithDays } from "../models/FilmWithDays";
import { CartCountContext } from "../context/CartCountContext";

function Films() {
  const { t } = useTranslation();
  const [loading, setLoading] = useState<boolean>(true);
  const [films, setFilms] = useState<Film[]>([]);
  const { increaseCount } = useContext(CartCountContext);
  // const [form, setForm] = useState({
  //   title: "",
  //   type: "NEW" as FilmType,
  //   description: "",
  //   inStock: true,
  // });

  const addToCart = (film: Film) => {
    const selectedFilms: FilmWithDays[] = JSON.parse(
      localStorage.getItem("cart") || "[]"
    );
    const existsFilm = selectedFilms.find((sf) => sf.film.id === film.id);

    if (!existsFilm) {
      selectedFilms.push({ film: film, days: 1 });
      localStorage.setItem("cart", JSON.stringify(selectedFilms));
      toast.success(`${film.title} - ${t("toast.films.added")}`);
      increaseCount();
    } else {
      toast.warn(`${t("toast.films.alreadyAdded")}`);
    }
  };

  // const handleAddFilmSubmit = () => {
  //   apiFetch<Film[]>("/films", { method: "POST", body: form }).then((res) =>
  //     setFilms(res)
  //   );
  // };
  // const handleAddFormChange = (
  //   e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  // ) => {
  //   const { name, value } = e.target;
  //   setForm((prev) => ({ ...prev, [name]: value }));
  // };

  useEffect(() => {
    apiFetch<Film[]>("/films?inStock=true")
      .then((res) => setFilms(res))
      .catch(() => toast.error(t("toast.error")))
      .finally(() => setLoading(false));
  }, []);

  return (
    <>
      <ToastContainer
        position="bottom-right"
        autoClose={3000}
        hideProgressBar={true}
        newestOnTop={true}
        closeOnClick={true}
      />
      {loading ? (
        <Skeleton />
      ) : (
        <Box
          sx={{
            display: "flex",
            flexWrap: "wrap",
            gap: 2,
            justifyContent: "center",
          }}
        >
          {films.map((film) => (
            <Card
              sx={{
                minHeight: 250,
                width: 250,
                display: "flex",
                flexDirection: "column",
              }}
              key={film.id}
            >
              <CardContent sx={{ flexGrow: 1 }}>
                <Typography variant="h5">{film.title}</Typography>
              </CardContent>
              <CardActions>
                <Button variant="contained" onClick={() => addToCart(film)}>
                  {t("button.addToCart")}
                </Button>
              </CardActions>
            </Card>
          ))}
        </Box>
      )}
    </>
  );
}

export default Films;
