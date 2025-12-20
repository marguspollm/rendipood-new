import {
  TableRow,
  TableCell,
  Collapse,
  Box,
  Table,
  TableHead,
  TableBody,
  Checkbox,
} from "@mui/material";
import React from "react";
import type { Rental } from "../../models/Rental";
import type { RentalFilm } from "../../models/RentalFilm";
import { useTranslation } from "react-i18next";

function RentalRow({ row }: { row: Rental }) {
  const { t } = useTranslation();
  const [open, setOpen] = React.useState(false);

  return (
    <>
      <TableRow onClick={() => setOpen(!open)}>
        <TableCell></TableCell>
        <TableCell>{row.id}</TableCell>
        <TableCell>{row.initialFee}</TableCell>
        <TableCell>{row.lateFee}</TableCell>
      </TableRow>

      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={3}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box margin={2}>
              <Table size="small">
                <TableHead>
                  <TableRow>
                    <TableCell>{t("rental.filmName")}</TableCell>
                    <TableCell>{t("rental.initialDays")}</TableCell>
                    <TableCell>{t("rental.lateDays")}</TableCell>
                    <TableCell>{t("rental.returned")}</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {row.rentalFilms.map((rentalFilm: RentalFilm, i: number) => (
                    <TableRow key={i}>
                      <TableCell>{rentalFilm.film.title}</TableCell>
                      <TableCell>{rentalFilm.initialDays}</TableCell>
                      <TableCell>{rentalFilm.lateDays}</TableCell>
                      <TableCell>
                        <Checkbox disabled checked={rentalFilm.returned} />
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </>
  );
}

export default RentalRow;
