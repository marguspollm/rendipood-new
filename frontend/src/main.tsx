import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import "./i18n/i18n";
import App from "./App.tsx";
import { BrowserRouter } from "react-router-dom";
import { ThemeProvider } from "@mui/material";
import { theme } from "./themes/themes.ts";
import { CartCountContextProvider } from "./context/CartCountContextProvider.tsx";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <CartCountContextProvider>
      <ThemeProvider theme={theme}>
        <BrowserRouter>
          <App />
        </BrowserRouter>
      </ThemeProvider>
    </CartCountContextProvider>
  </StrictMode>
);

//Rendipood -
// erinevad lehed (routimine)
// tõlge
// darkmode/lightmode - localstorage
// font kasutusele  (google fonts)
// mui, tailwind, bootstrap ( igale projektile erinev)
// react toastify
// react hot toast (nt toote lisamine, kustutamine, vea teaded)
