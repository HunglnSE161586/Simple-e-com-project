import { PaginatedResponse } from "../types/PaginatedResponse";
import { CreateUser, User, UserLogin } from "../types/User";
import axiosInstance from "./AxiosInstance";
export const registerUser= async (userData:CreateUser):Promise<User> =>{
    try {
        const response = await axiosInstance.post("/users/register", userData);
        return response.data;
      } catch (error) {
        console.error("Error registering user:", error);
        throw error; 
      }
}
export const loginUser= async (userData:UserLogin):Promise<string> =>{
  try {
      const response = await axiosInstance.post("/auth/login", userData);
      return response.data;
    } catch (error) {
      console.error("Error login user:", error);
      throw error; 
    }
}
export const logoutUser=async ():Promise<string>=>{
  try {
    const response = await axiosInstance.post("/auth/logout");
    return response.data;
  } catch (error) {
    console.error("Error logout user:", error);
    throw error; 
  }
}
export const getUserById=async(userId:number):Promise<User>=>{
  try {
    const response = await axiosInstance.get(`/users/${userId}`);
    return response.data;
  } catch (error) {
    console.error("Error get user by id:", error);
    throw error; 
  }
}
export const getUsersPaged=async(page: number, size: number):Promise<PaginatedResponse<User>> =>{
  try {
    const response = await axiosInstance.get(`/users?page=${page}&size=${size}`);
    return response.data;
  } catch (error) {
    console.error("Error get paged user:", error);
    throw error; 
  }
}