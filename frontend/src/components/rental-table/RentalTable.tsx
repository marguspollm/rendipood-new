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
import RentalRow from "./RentalRow";
import { useTranslation } from "react-i18next";

function RentalTable({ rentals }: { rentals: Rental[] }) {
  const { t } = useTranslation();

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
          {rentals.map((row: Rental, index: number) => (
            <RentalRow row={row} key={index}></RentalRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export default RentalTable;
