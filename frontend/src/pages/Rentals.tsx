import { useState, useEffect } from "react";
import RentalTable from "../components/rental-table/RentalTable";
import type { Rental } from "../models/Rental";
import { apiFetch } from "../services/api";

function Rentals() {
  const [rentals, setRentals] = useState<Rental[]>([]);

  useEffect(() => {
    apiFetch<Rental[]>("/rentals").then((res) => setRentals(res));
  }, []);
  return <RentalTable rentals={rentals} />;
}

export default Rentals;
