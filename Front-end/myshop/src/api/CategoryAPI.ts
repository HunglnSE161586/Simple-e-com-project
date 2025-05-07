import axiosInstance from "./AxiosInstance";
import { Category, CreateCategory } from "../types/Category";
import { PaginatedResponse } from "../types/PaginatedResponse";

export const fetchAllCategories = async (): Promise<Category[]> => {
  const response = await axiosInstance.get("/categories/all-test");
  return response.data;
};
export const fetchPagedCategories = async (page: number, size: number):Promise<PaginatedResponse<Category>>=>{
    const response=await axiosInstance.get(`/categories?page=${page}&size=${size}`);
    return response.data;
}
export const createCategory = async (createCategory:CreateCategory):Promise<Category>=>{
  try{
    const response=await axiosInstance.post(`/categories`,createCategory);
    return response.data;
  }catch(error){
    console.log("Error in create category:"+error);
    throw error;
  }
}
export const updateCategory = async (categoryId:number, createCategory:CreateCategory):Promise<Category>=>{
  try{
    const response=await axiosInstance.put(`/categories/${categoryId}`,createCategory);
    return response.data;
  }catch(error){
    console.log("Error in update category:"+error);
    throw error;
  }
}