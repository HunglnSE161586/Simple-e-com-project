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
export const fetchPagedProductByCategory = async (categoryId: number, page: number, size: number): Promise<PaginatedResponse<Product>> => {
  const response = await axiosInstance.get(`/products/category/${categoryId}?page=${page}&size=${size}`);
  return response.data;
}
export const fetchPagedProductIsFeature = async (page: number, size: number, isFeature: boolean): Promise<PaginatedResponse<Product>> => {
  const response = await axiosInstance.get(`/products/paged?page=${page}&size=${size}&isFeature=${isFeature}`);
  return response.data;
}
export const softDeleteProduct = async (productId: number): Promise<Product> => {
  try {
    const response = await axiosInstance.delete(`/products/${productId}`);
    return response.data;
  } catch (error) {
    console.log("Error delete product by id:", error);
    throw error;
  }
}
export const softRestoreProduct = async (productId: number): Promise<Product> => {
  try {
    const response = await axiosInstance.patch(`/products/${productId}/restore`);
    return response.data;
  } catch (error) {
    console.error("Error restore product by id:", error);
    throw error;
  }
}