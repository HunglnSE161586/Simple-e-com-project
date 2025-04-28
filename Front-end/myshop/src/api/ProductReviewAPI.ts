import { ProductReview, ProductReviewCreateRequest } from "../types/ProductReview";
import axiosInstance from "./AxiosInstance";
export const fetchProductReviewByProductId=async (productId:number):Promise<ProductReview[]>=>{
    const response=await axiosInstance.get(`/product-reviews/product/${productId}`)
    return response.data;
}
export const postProductReview=async (review:ProductReviewCreateRequest):Promise<ProductReview[]>=>{
    const response=await axiosInstance.post(`/product-reviews`,review)
    return response.data;
}