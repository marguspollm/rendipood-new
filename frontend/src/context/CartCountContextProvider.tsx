import { type ReactNode, useState } from "react";
import type { Film } from "../models/Film";
import { CartCountContext } from "./CartCountContext";

// Provider abil määrb ära milline component saab kätte (skoobi)
export const CartCountContextProvider = ({
  children,
}: {
  children: ReactNode;
}) => {
  const [count, setCount] = useState(countCartItems());

  function countCartItems() {
    let count = 0;
    const cart: Film[] = JSON.parse(localStorage.getItem("cart") || "[]");

    cart.forEach(() => count++);

    return count;
  }

  return (
    <CartCountContext.Provider value={{ count, setCount }}>
      {children}
    </CartCountContext.Provider>
  );
};
