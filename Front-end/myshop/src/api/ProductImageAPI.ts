import { ProductImage, UpdateProductImage } from "../types/ProductImage";
import axiosInstance from "./AxiosInstance";

export const updateProductImage = async (id: number,updateProductImage:UpdateProductImage): Promise<ProductImage> => {
    try {
      const response = await axiosInstance.put(`/product-images/${id}`,updateProductImage);
      return response.data;
    } catch (error) {
      console.error("Error update product image by id:", error);
      throw error;
    }
  }