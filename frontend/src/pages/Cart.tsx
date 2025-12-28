import { Box, Button, TextField, Typography } from "@mui/material";
import { apiFetch } from "../services/api";
import { ToastContainer, toast } from "react-toastify";
import { useTranslation } from "react-i18next";
import type { FilmWithDays } from "../models/FilmWithDays";
import { useContext, useState } from "react";
import { CartCountContext } from "../context/CartCountContext";

function Cart() {
  const { t } = useTranslation();
  const { count, setCount } = useContext(CartCountContext);
  const [cartItems, setCartItems] = useState<FilmWithDays[]>(
    JSON.parse(localStorage.getItem("cart") || "[]")
  );

  const removeFromCart = (index: number) => {
    const updatedCart = [...cartItems];
    updatedCart.splice(index, 1);
    localStorage.setItem("cart", JSON.stringify(updatedCart));
    setCartItems(updatedCart);
    toast.warn(`${t("toast.cart.removed")}`);
    setCount(count - 1);
  };

  const setFilmDays = (days: number, index: number) => {
    setCartItems((prev) =>
      prev.map((f, i) => (i === index ? { ...f, days } : f))
    );
  };

  const orderFilms = async () => {
    const payload = cartItems.map((item) => ({
      filmId: item.film.id,
      days: item.days,
    }));

    if (payload.length == 0) {
      toast.error(`${t("toast.cart.emptyCart")}`);
      return;
    }

    await apiFetch("/start-rental", {
      method: "POST",
      body: payload,
    })
      .then((res) => {
        toast.success(`${t("toast.cart.ordered")} - ${res}€`);
        setCartItems([]);
        setCount(0);
        localStorage.removeItem("cart");
      })
      .catch(() => {
        toast.error(`${t("toast.error")}`);
      });
  };

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
        {cartItems.length == 0 ? (
          <Typography variant="body1" color="primary">
            {t("cart.isEmpty")}
          </Typography>
        ) : (
          <>
            {cartItems.map((item, index) => {
              return (
                <Box
                  key={item.film.id}
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
                      {item.film.title} ... HIND?
                    </Typography>
                    <TextField
                      type="number"
                      label={t("cart.days")}
                      value={cartItems[index].days}
                      onChange={(e) =>
                        setFilmDays(Number(e.target.value), index)
                      }
                      sx={{ width: 100, mt: 1 }}
                    />
                  </Box>
                  <Button
                    onClick={() => removeFromCart(index)}
                    variant="contained"
                    // color="secondary"
                  >
                    {t("button.remove")}
                  </Button>
                </Box>
              );
            })}
          </>
        )}

        {cartItems.length > 0 && (
          <Button
            onClick={() => orderFilms()}
            variant="contained"
            sx={{ mt: 3 }}
          >
            {t("button.order")}
          </Button>
        )}
      </Box>
    </>
  );
}

export default Cart;
