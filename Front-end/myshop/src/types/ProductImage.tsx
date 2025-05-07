export interface ProductImage {
  id: number;
  productId: number;
  imageUrl: string;
  altText: string;
  displayOrder: number;
  createdAt: string;
}
export interface CreateProductImage {
  imageUrl: string;
  altText: string;
  displayOrder: number;
}
export interface UpdateProductImage {
  imageUrl: string;
  altText: string;
}