import { createTheme } from "@mui/material";

export const theme = createTheme({
  typography: {
    fontFamily: `"Nunito Sans", sans-serif`,
    fontSize: 14,
    fontWeightLight: 300,
    fontWeightRegular: 400,
    fontWeightMedium: 500,
  },
});

// export const darkTheme = createTheme({
//   palette: {
//     mode: "dark",
//     primary: {
//       main: "#0A0F24",
//     },
//     secondary: {
//       main: "#2D9CDB",
//     },
//     background: {
//       default: "#2F3645",
//     },
//     text: {
//       primary: "#F5F7FA",
//       secondary: "#D0D4DA",
//     },
//   },
//   typography: {
//     fontFamily: `"Nunito Sans", sans-serif`,
//     fontSize: 14,
//     fontWeightLight: 300,
//     fontWeightRegular: 400,
//     fontWeightMedium: 500,
//   },
// });

// export const lightTheme = createTheme({
//   palette: {
//     mode: "light",
//     primary: {
//       main: "#D97A2B",
//     },
//     secondary: {
//       main: "#7A8450",
//     },
//     background: {
//       default: "#E8D7C1",
//       paper: "#F4E8D8",
//     },
//     text: {
//       primary: "#5A3E36",
//       secondary: "#7A6A63",
//     },
//   },
//   typography: {
//     fontFamily: `"Nunito Sans", sans-serif`,
//     fontSize: 14,
//     fontWeightLight: 300,
//     fontWeightRegular: 400,
//     fontWeightMedium: 500,
//   },
// });
export const darkTheme = createTheme({
  palette: {
    mode: "dark",
  },
});
