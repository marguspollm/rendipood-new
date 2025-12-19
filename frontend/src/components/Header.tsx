import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { useState, useEffect } from "react";

const Header = () => {
  const { t, i18n } = useTranslation();
  const [theme, setTheme] = useState(determineColorMode());

  function updateLanguage(newLang: string) {
    i18n.changeLanguage(newLang);
    localStorage.setItem("language", newLang);
  }

  function determineColorMode() {
    return localStorage.getItem("theme") || "light";
  }

  useEffect(() => {
    document.documentElement.setAttribute("data-bs-theme", theme);
    localStorage.setItem("theme", theme);
  }, [theme]);

  return (
    <div>
      <header>
        <nav>
          <div>
            <Link to={"/"}>Webshop</Link>

            <button type="button">
              <span></span>
            </button>
            <div id="navbarNav">
              <ul>
                <li>
                  <Link to={"/products"}>{t("header.products")}</Link>
                </li>
                <li>
                  <Link to={"/persons"}>{t("header.persons")}</Link>
                </li>
                <li>
                  <Link to={"/orders"}>{t("header.orders")}</Link>
                </li>
                <li>
                  <Link to={"/category"}>{t("header.category")}</Link>
                </li>
              </ul>
            </div>

            <button
              onClick={() => setTheme(theme === "light" ? "dark" : "light")}
            >
              {" "}
              {theme === "light" ? "Dark" : "Light"} Mode{" "}
            </button>

            <div>
              <button onClick={() => updateLanguage("et")}>Eesti</button>
              <button onClick={() => updateLanguage("en")}>English</button>
            </div>
          </div>
        </nav>
      </header>
    </div>
  );
};

export default Header;
