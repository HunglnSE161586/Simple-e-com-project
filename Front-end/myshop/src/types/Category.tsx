
export interface Category {
  categoryId: number;
  categoryName: string;
  description: string;
  createdAt: string;
  image: string;
}
export interface CreateCategory {
  categoryName: string;
  description: string;
  image: string;
}