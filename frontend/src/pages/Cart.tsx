import { Box, Button, TextField, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { apiFetch } from "../services/api";
import { ToastContainer, toast } from "react-toastify";
import { useTranslation } from "react-i18next";
import type { RentalFilmDTO } from "../models/RentalFilmDTO";

function Cart() {
  const { t } = useTranslation();
  const [cartItems, setCartItems] = useState<RentalFilmDTO[]>([]);

  const removeFromCart = (id: number) => {
    const updated = cartItems.filter((item) => item.id !== id);
    setCartItems(updated);
    localStorage.setItem("cart", JSON.stringify(updated));
    toast.warn(`${t("alert.cart.removed")}`);
  };

  const setFilmDays = (days: number, id: number) => {
    setCartItems((prev) => prev.map((f) => (f.id === id ? { ...f, days } : f)));
  };

  const orderFilms = async () => {
    const payload = cartItems.map((film) => ({
      filmId: film.id,
      days: film.days,
    }));

    if (payload.length == 0) {
      toast.error(`${t("alert.cart.emptyCart")}`);
      return;
    }

    await apiFetch("/start-rental", {
      method: "POST",
      body: payload,
    }).then(() => {
      toast.success(`${t("alert.cart.ordered")}`);
      setCartItems([]);
      localStorage.removeItem("cart");
    });
  };

  useEffect(() => {
    const storedFilms: RentalFilmDTO[] = JSON.parse(
      localStorage.getItem("cart") ?? "[]"
    );

    setCartItems(storedFilms);
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
      <Box sx={{ p: 3, maxWidth: 700, mx: "auto" }}>
        <Typography variant="h4" color="secondary" sx={{ mb: 3 }}>
          {t("cart.title")}
        </Typography>
        {cartItems.map((item) => {
          return (
            <Box
              key={item.id}
              sx={{
                p: 2,
                mb: 2,
                borderRadius: 2,
                border: "1px solid",
                borderColor: "divider",
                display: "flex",
                alignItems: "center",
                justifyContent: "space-between",
                gap: 2,
              }}
            >
              <Box sx={{ flexGrow: 1 }}>
                <Typography variant="body1" color="secondary">
                  {item.title} ... HIND?
                </Typography>
                <TextField
                  type="number"
                  label={t("cart.days")}
                  value={item.days}
                  onChange={(e) => setFilmDays(Number(e.target.value), item.id)}
                  sx={{ width: 100, mt: 1 }}
                />
              </Box>
              <Button
                onClick={() => removeFromCart(item.id)}
                variant="contained"
                // color="secondary"
              >
                {t("button.remove")}
              </Button>
            </Box>
          );
        })}

        {cartItems.length > 0 && (
          <Button
            onClick={() => orderFilms()}
            variant="contained"
            sx={{ mt: 3 }}
            // color="secondary"
          >
            {t("button.order")}
          </Button>
        )}
      </Box>
    </>
  );
}

export default Cart;
