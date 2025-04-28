import axiosInstance from "./AxiosInstance";
import { Category } from "../types/Category";
import { PaginatedResponse } from "../types/PaginatedResponse";

export const fetchAllCategories = async (): Promise<Category[]> => {
  const response = await axiosInstance.get("/categories");
  return response.data;
};
export const fetchPagedCategories = async ():Promise<PaginatedResponse<Category>>=>{
    const response=await axiosInstance.get("/categories/paged");
    return response.data;
}