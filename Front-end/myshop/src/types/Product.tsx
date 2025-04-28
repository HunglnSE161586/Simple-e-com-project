// types/Product.ts
import { Category } from "./Category";
import { ProductImage } from "./ProductImage";
import { ProductReview } from "./ProductReview";
export interface Product {
  productId: number;
  productName: string;
  description: string;
  price: number;
  isActive: boolean;
  isFeatured: boolean;
  createdAt: string; // or Date
  updatedAt: string; // or Date
  stock: number;
  categoryId: number;
  productImagePOJO?:ProductImage; //optional
}

export interface ProductDetail {
  productId: number;
  productName: string;
  description: string;
  price: number;
  isActive: boolean;
  isFeatured: boolean;
  createdAt: string;
  updatedAt: string;
  stock: number;
  productImagePOJOS: ProductImage[];
  categoryPOJO: Category;
  productReviewPOJOS: ProductReview[];
}