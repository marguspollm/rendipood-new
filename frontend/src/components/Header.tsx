import { useTranslation } from "react-i18next";
import AppBar from "@mui/material/AppBar";
import {
  Badge,
  Box,
  Button,
  FormControlLabel,
  FormGroup,
  Switch,
  Toolbar,
  Typography,
  useColorScheme,
} from "@mui/material";
import LanguageToggle from "./LanguageToggle";
import { Link } from "react-router-dom";
import { useContext } from "react";
import { CartCountContext } from "../context/CartCountContext";

const Header = () => {
  const { t } = useTranslation();
  const { mode, setMode } = useColorScheme();
  const { count } = useContext(CartCountContext);

  const determineTheme = () => {
    return localStorage.getItem("mui-mode") || "light";
  };

  const changeTheme = () => {
    setMode(mode === "light" ? "dark" : "light");
  };

  const checkedTheme = () => {
    return determineTheme() === "dark" ? true : false;
  };

  return (
    <AppBar
      position="sticky"
      sx={{
        backdropFilter: "blur(8px)",
        backgroundColor: "#ffffffcc",
      }}
    >
      <Toolbar disableGutters>
        <Typography
          sx={{ mr: 4, fontWeight: 800, padding: "25px" }}
          variant="h5"
          color="primary"
        >
          Rendipood
        </Typography>
        <Box sx={{ flexGrow: 1, display: "flex", gap: 2 }}>
          <Button
            key={"films"}
            component={Link}
            to={"/films"}
            variant="text"
            color="secondary"
          >
            {t("header.films")}
          </Button>

          <Button
            key={"cart"}
            component={Link}
            to={"/cart"}
            variant="text"
            color="secondary"
          >
            <Badge color="secondary" badgeContent={count}>
              {t("header.cart")}
            </Badge>
          </Button>

          <Button
            key={"rentals"}
            component={Link}
            to={"/rentals"}
            variant="text"
            color="secondary"
          >
            {t("header.rentals")}
          </Button>
          <Button
            key={"returnals"}
            component={Link}
            to={"/returnals"}
            variant="text"
            color="secondary"
          >
            {t("header.returnals")}
          </Button>
        </Box>
        <Box sx={{ flexGrow: 0 }}>
          <LanguageToggle />
          <FormGroup>
            <FormControlLabel
              checked={checkedTheme()}
              control={<Switch size="small" onChange={changeTheme} />}
              label={
                <Typography variant="body1" color="primary">
                  Theme
                </Typography>
              }
            />
          </FormGroup>
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Header;
