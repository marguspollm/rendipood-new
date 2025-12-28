import { createContext } from "react";

export const CartCountContext = createContext({
  count: 0,
  setCount: (newCount: number) => {
    console.log(newCount);
  },
});
