import { Menu, MenuItem, Typography, Button } from "@mui/material";
import { useState, type MouseEvent } from "react";
import { useTranslation } from "react-i18next";
import rawLanguages from "../i18n/languages.json";
import type { LanguageInfo } from "../models/LanguageInfo";

function LanguageToggle() {
  const { i18n } = useTranslation();
  const languages = rawLanguages as Record<string, LanguageInfo>;
  const [anchorElLanguage, setAnchorElLanguage] = useState<null | HTMLElement>(
    null
  );
  const [currentLanguage, setCurrentLanguage] = useState(determineLanguage());
  const languageLabel = languages[currentLanguage].shortLabel;

  const handleOpenLanguageMenu = (event: MouseEvent<HTMLElement>) => {
    setAnchorElLanguage(event.currentTarget);
  };

  const handleCloseLanguageMenu = () => {
    setAnchorElLanguage(null);
  };

  function determineLanguage() {
    return localStorage.getItem("language") || "en";
  }

  const updateLanguage = (newLang: string) => {
    i18n.changeLanguage(newLang);
    localStorage.setItem("language", newLang);
  };

  const changeLanguage = (lang: string) => {
    updateLanguage(lang);
    setCurrentLanguage(lang);
    handleCloseLanguageMenu();
  };

  return (
    <>
      {" "}
      <Button onClick={handleOpenLanguageMenu} sx={{ p: 0 }}>
        <Typography sx={{ textAlign: "center", color: "#00000" }}>
          {languageLabel}
        </Typography>
      </Button>
      <Menu
        sx={{ mt: "45px" }}
        id="menu-appbar"
        anchorEl={anchorElLanguage}
        anchorOrigin={{
          vertical: "top",
          horizontal: "right",
        }}
        keepMounted
        transformOrigin={{
          vertical: "top",
          horizontal: "right",
        }}
        open={Boolean(anchorElLanguage)}
        onClose={handleCloseLanguageMenu}
      >
        {Object.values(languages).map((lng) => (
          <MenuItem
            key={lng.code}
            onClick={() => {
              changeLanguage(lng.code);
            }}
          >
            <Typography sx={{ textAlign: "center" }}>{lng.label}</Typography>
          </MenuItem>
        ))}
        {/* <MenuItem
          key={"et"}
          onClick={() => {
            handleCloseLanguageMenu();
            updateLanguage("et");
          }}
        >
          <Typography sx={{ textAlign: "center" }}>Eesti</Typography>
        </MenuItem>
        <MenuItem
          key={"en"}
          onClick={() => {
            handleCloseLanguageMenu();
            updateLanguage("en");
          }}
        >
          <Typography sx={{ textAlign: "center" }}>English</Typography>
        </MenuItem> */}
      </Menu>
    </>
  );
}

export default LanguageToggle;
