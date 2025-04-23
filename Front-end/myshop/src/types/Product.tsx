// types/Product.ts
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
  image:string;
}
