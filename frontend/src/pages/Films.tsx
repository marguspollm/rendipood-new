import { useState, useEffect } from "react";
import type { Film } from "../models/Film";
import { apiFetch } from "../services/api";
import {
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  Typography,
} from "@mui/material";
import { toast, ToastContainer } from "react-toastify";

function Films() {
  const [films, setFilms] = useState<Film[]>([]);
  const { t } = useTranslation();

  const addToCart = (film: Film) => {
    const selectedFilms: Film[] = JSON.parse(
      localStorage.getItem("cart") ?? "[]"
    );
    const existsFilm = selectedFilms.some((i) => i.id === film.id);
    if (!existsFilm) {
      const filmWithDays = { ...film, days: 1 };
      selectedFilms.push(filmWithDays);
      localStorage.setItem("cart", JSON.stringify(selectedFilms));
      toast.info(`${film.title} - ${t("alert.films.added")}`);
    } else {
      toast.warn(`${t("alert.films.alreadyAdded")}`);
    }
  };

  useEffect(() => {
    apiFetch<Film[]>("/films?inStock=true").then((res) => setFilms(res));
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
              // bgcolor: "primary.main",
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
              <Button
                variant="contained"
                // color="secondary"
                onClick={() => addToCart(film)}
              >
                {t("button.addToCart")}
              </Button>
            </CardActions>
          </Card>
        ))}
      </Box>
    </>
  );
}
import { useTranslation } from "react-i18next";

export default Films;
