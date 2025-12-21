import {
  Box,
  Typography,
  TextField,
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  type SelectChangeEvent,
} from "@mui/material";
import { useEffect, useState } from "react";
import { apiFetch } from "../services/api";
import { useTranslation } from "react-i18next";
import type { RentalFilm } from "../models/RentalFilm";
import { toast, ToastContainer } from "react-toastify";

function Returnal() {
  const { t } = useTranslation();
  const [days, setDays] = useState(1);
  const [rentedFilms, setRentedFilms] = useState<RentalFilm[]>([]);

  const [selectedFilm, setSelectedFilm] = useState("");

  const handleChange = (event: SelectChangeEvent) => {
    setSelectedFilm(event.target.value as string);
  };

  const handleReturn = () => {
    const payload = [
      {
        filmId: selectedFilm,
        days: days,
      },
    ];

    apiFetch("/end-rental", {
      method: "POST",
      body: payload,
    })
      .then((res) => {
        toast.success(`${t("toast.returnal.returned")} - ${res}€`);
        setSelectedFilm("");
        setDays(1);
        getRentedFilms();
      })
      .catch(() => toast.error(`${t("toast.error")}`));
  };

  const getRentedFilms = () => {
    apiFetch<RentalFilm[]>("/rented-films")
      .then((res) => setRentedFilms(res))
      .catch(() => toast.error(t("toast.error")));
  };

  useEffect(() => {
    getRentedFilms();
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
      <Box sx={{ p: 3, maxWidth: 600, mx: "auto" }}>
        <Typography variant="h4" sx={{ mb: 3 }}>
          {t("returnal.title")}
        </Typography>
        <Box
          sx={{
            p: 2,
            borderRadius: 2,
            border: "1px solid",
            borderColor: "divider",
            display: "flex",
            flexDirection: "column",
            gap: 2,
          }}
        >
          <FormControl fullWidth>
            <InputLabel id="film-select-label">
              {t("returnal.filmTitle")}
            </InputLabel>
            <Select
              labelId="film-select-label"
              id="film-select"
              value={selectedFilm}
              label={t("returnal.filmTitle")}
              onChange={handleChange}
            >
              {rentedFilms.map((rental) => {
                return (
                  <MenuItem value={rental.film.id}>
                    {rental.film.title}
                  </MenuItem>
                );
              })}
            </Select>
          </FormControl>

          <TextField
            type="number"
            label={t("returnal.daysRented")}
            value={days}
            onChange={(e) => setDays(Number(e.target.value))}
            sx={{
              width: 150,
              "& input": { textAlign: "center", fontWeight: 600 },
            }}
          />
          <Button variant="contained" onClick={handleReturn} sx={{ mt: 1 }}>
            {t("button.return")}
          </Button>
        </Box>
      </Box>
    </>
  );
}

export default Returnal;
