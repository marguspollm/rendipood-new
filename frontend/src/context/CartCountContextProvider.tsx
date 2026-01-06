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

  function increaseCount() {
    setCount(count + 1);
  }

  function decreaseCount() {
    setCount(count - 1);
  }

  function resetCount() {
    setCount(0);
  }

  return (
    <CartCountContext.Provider
      value={{ count, increaseCount, decreaseCount, resetCount }}
    >
      {children}
    </CartCountContext.Provider>
  );
};
