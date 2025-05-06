import axiosInstance from "./AxiosInstance";
import { Product, ProductDetail, UpdateProduct } from "../types/Product";
import { PaginatedResponse } from "../types/PaginatedResponse";
export const fetchAllProducts = async (): Promise<Product[]> => {
  const response = await axiosInstance.get("/products/all");
  return response.data;
};
export const fetchPagedProduct = async (page: number, size: number): Promise<PaginatedResponse<Product>> => {
  const response = await axiosInstance.get(`/products?page=${page}&size=${size}`);
  return response.data;
}
export const fetchPagedProductByCategory = async (categoryId: number, page: number, size: number): Promise<PaginatedResponse<Product>> => {
  const response = await axiosInstance.get(`/products/categories/${categoryId}?page=${page}&size=${size}`);
  return response.data;
}
export const fetchPagedProductIsFeature = async (page: number, size: number, isFeature: boolean): Promise<PaginatedResponse<Product>> => {
  const response = await axiosInstance.get(`/products?page=${page}&size=${size}&isFeature=${isFeature}`);
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
export const fetchProductDetail = async (productId:number): Promise<ProductDetail> => {
  const response = await axiosInstance.get(`/products/${productId}`);
  return response.data;
}
export const updateProduct = async (productId: number,updateProduct:UpdateProduct): Promise<Product> => {
  try {
    const response = await axiosInstance.put(`/products/${productId}`,updateProduct);
    return response.data;
  } catch (error) {
    console.error("Error update product by id:", error);
    throw error;
  }
}