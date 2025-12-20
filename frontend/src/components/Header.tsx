import { useTranslation } from "react-i18next";
import AppBar from "@mui/material/AppBar";
import { Badge, Box, Button, Toolbar, Typography } from "@mui/material";
import LanguageToggle from "./LanguageToggle";
import { Link } from "react-router-dom";

const Header = () => {
  const { t } = useTranslation();
  //   const [theme, setTheme] = useState(determineColorMode());

  //   function determineColorMode() {
  //     return localStorage.getItem("theme") || "light";
  //   }

  //   useEffect(() => {
  //     document.documentElement.setAttribute("data-bs-theme", theme);
  //     localStorage.setItem("theme", theme);
  //   }, [theme]);

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
          sx={{ mr: 4, color: "#000000", fontWeight: 800, padding: "25px" }}
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
          <Badge>
            <Button
              key={"cart"}
              component={Link}
              to={"/cart"}
              variant="text"
              color="secondary"
            >
              {t("header.cart")}
            </Button>
          </Badge>

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
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Header;
