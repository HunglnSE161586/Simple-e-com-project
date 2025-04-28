import axiosInstance from "./AxiosInstance";
import { Product } from "../types/Product";
import { PaginatedResponse } from "../types/PaginatedResponse";
export const fetchAllProducts = async (): Promise<Product[]> => {
  const response = await axiosInstance.get("/products");
  return response.data;
};
export const fetchPagedProduct = async (page: number, size: number): Promise<PaginatedResponse<Product>> => {
  const response = await axiosInstance.get(`/products/paged?page=${page}&size=${size}`);
  return response.data;
}
export const fetchPagedProductByCategory = async (categoryId:number,page: number, size: number): Promise<PaginatedResponse<Product>> => {
  const response = await axiosInstance.get(`/products/category/${categoryId}?page=${page}&size=${size}`);
  return response.data;
}