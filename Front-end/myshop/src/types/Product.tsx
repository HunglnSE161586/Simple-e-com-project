// types/Product.ts
import { Category } from "./Category";
import { CreateProductImage, ProductImage } from "./ProductImage";
import { ProductReview } from "./ProductReview";
export interface Product {
  id: number;
  productName: string;
  description: string;
  price: number;
  isActive: boolean;
  isFeatured: boolean;
  createdAt: string; // or Date
  updatedAt: string; // or Date
  stock: number;
  categoryId: number;
  productImagePOJO?: ProductImage; //optional
}

export interface ProductDetail {
  id: number;
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
export interface CreateProduct {
  productName: string;
  description: string;
  price: number;
  stock: number;
  categoryId: number;
  productImages: CreateProductImage[];
}
export interface UpdateProduct {
  productName: string;
  description: string;
  price: number;
  stock: number;
  categoryId: number;
  isFeatured: boolean;
}