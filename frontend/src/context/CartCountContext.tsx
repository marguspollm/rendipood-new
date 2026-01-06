import { createContext } from "react";

export const CartCountContext = createContext({
  count: 0,
  increaseCount: () => {},
  decreaseCount: () => {},
  resetCount: () => {},
});
