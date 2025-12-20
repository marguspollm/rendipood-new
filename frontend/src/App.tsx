import "./App.css";
import { Routes, Route } from "react-router-dom";
import { Box } from "@mui/material";
import Footer from "./components/Footer";
import Header from "./components/Header";
import Films from "./pages/Films";
import Cart from "./pages/Cart";
import Rentals from "./pages/Rentals";
import Returnal from "./pages/Returnal";

function App() {
  return (
    <>
      <Box
        sx={{
          bgcolor: "background.default",
          display: "flex",
          flexDirection: "column",
          justifyContent: "space-between",
          minHeight: "100vh",
        }}
      >
        <Header />
        <Box sx={{ flexGrow: 1, padding: 5 }}>
          <Routes>
            <Route path="/" element={<Films />} />
            <Route path="/films" element={<Films />} />
            <Route path="/cart" element={<Cart />} />
            <Route path="/rentals" element={<Rentals />} />
            <Route path="/returnals" element={<Returnal />} />
          </Routes>
        </Box>
        <Footer />
      </Box>
    </>
  );
}

export default App;
