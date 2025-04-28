export interface ProductReview{
    reviewId: number;
    productId: number;
    userId: number;
    rating: number;
    reviewText: string;
    createdAt: string;
  }
export interface ProductReviewCreateRequest{
    productId: number;
    userId: number;
    rating: number;
    reviewText: string;
}