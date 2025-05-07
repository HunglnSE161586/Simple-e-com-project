export interface CreateUser{
    email:string;
    firstName:string;
    lastName:string;
    password:string;
}
export interface User {
    userId: number;  
    email: string;
    firstName: string;
    lastName: string;
    isActive: boolean;
    createdAt: string;  
    updatedAt: string;  
}
export interface UserLogin{
    username:string;
    password:string;
}
