import axiosInstance from "./AxiosInstance";
import { Product } from "../types/Product";

export const fetchAllProducts = async (): Promise<Product[]> => {
  const response = await axiosInstance.get("/products");
  return response.data;
};
