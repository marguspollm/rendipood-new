import {
  TableContainer,
  Paper,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
} from "@mui/material";
import type { Rental } from "../../models/Rental";
import { useEffect, useState } from "react";
import RentalRow from "./RentalRow";
import { apiFetch } from "../../services/api";
import { useTranslation } from "react-i18next";

function RentalTable() {
  const { t } = useTranslation();
  const [rentals, setRentals] = useState<Rental[]>([]);

  useEffect(() => {
    apiFetch<Rental[]>("/rentals").then((res) => setRentals(res));
  }, []);

  return (
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell />
            <TableCell>{t("rental.id")}</TableCell>
            <TableCell>{t("rental.initialFee")}</TableCell>
            <TableCell>{t("rental.lateFee")}</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rentals.map((row: Rental) => (
            <RentalRow row={row}></RentalRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export default RentalTable;
