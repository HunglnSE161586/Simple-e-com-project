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