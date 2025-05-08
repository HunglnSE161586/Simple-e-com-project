import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import type { ProductDetail } from "../types/Product";
import axiosInstance from "../api/AxiosInstance";
import { ProductReview, ProductReviewCreateRequest } from "../types/ProductReview";
import { getToken } from "../auth/Token";
import { toast } from "react-toastify";
import { getUserId } from "../auth/JwtDecode";

const ProductDetail: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [product, setProduct] = useState<ProductDetail | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [isLoggedIn, setIsLogin] = useState(!!getToken());
    // Review form states
    const [reviewText, setReviewText] = useState("");
    const [rating, setRating] = useState(5);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [reviewError, setReviewError] = useState("");
    const [userId, setUserId] = useState<number>(0);
    const calculateAverageRating = (reviews: ProductReview[]): number => {
        if (reviews.length === 0) return 0;  // No reviews, return 0

        const totalRating = reviews.reduce((sum, review) => sum + review.rating, 0);
        return totalRating / reviews.length;
    };
    

    const averageRating = product?.productReviewPOJOS ? calculateAverageRating(product.productReviewPOJOS) : 0;

    useEffect(() => {
        const fetchProduct = async () => {
            try {
                const response = await axiosInstance.get(`/products/${id}`);
                setProduct(response.data);
            } catch (err) {
                setError("Failed to fetch product.");
            } finally {
                setLoading(false);
            }
        };

        fetchProduct();
    }, [id]);

    useEffect(() => {
        const token = getToken();
        if (token) {
            setUserId(getUserId(token) || 0);
            setIsLogin(true);
        } else {
            setUserId(0);
            setIsLogin(false);
        }
    }, []);

    if (loading) return <div className="text-center mt-5">Loading...</div>;
    if (error) return <div className="alert alert-danger text-center">{error}</div>;
    if (!product) return <div className="text-center mt-5">Product not found.</div>;

    const handleSubmitReview = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!isLoggedIn) {
            toast.error('Please log in to leave a review.')
            return;
        }

        if (!reviewText.trim()) {
            setReviewError("Please enter a review text.");
            return;
        }

        if (!id) {
            setReviewError("Product ID is missing.");
            return;
        }

        setIsSubmitting(true);
        setReviewError("");

        try {
            const reviewData: ProductReviewCreateRequest = {
                productId: parseInt(id),
                userId: userId,
                rating: rating,
                reviewText: reviewText.trim()
            };

            // Submit the review
            await axiosInstance.post('/product-reviews', reviewData);

            // Refresh product data to get updated reviews
            const response = await axiosInstance.get(`/products/${id}`);
            setProduct(response.data);

            // Reset form
            setReviewText("");
            setRating(5);

        } catch (err: any) {
            const errorMessage =
                err.response?.data?.reviewText || err.response?.data || 'An unexpected error occurred while submitting the review.';
            toast.error(`Error submitting review: ${errorMessage}`);
            console.error("Error submitting review:", err);
        } finally {
            setIsSubmitting(false);
        }
    };
    const renderStarRating = () => {
        return (
            <div className="mb-3">
                <label className="form-label">Rating:</label>
                <div>
                    {[1, 2, 3, 4, 5].map((star) => (
                        <button
                            key={star}
                            type="button"
                            className="btn btn-link p-0 me-1"
                            onClick={() => setRating(star)}
                        >
                            {star <= rating ? "★" : "☆"}
                        </button>
                    ))}
                </div>
            </div>
        );
    };
    const renderReviewForm = () => {
        if (!isLoggedIn) {
            return (
                <div className="alert alert-info mt-4">
                    Please log in to leave a review.
                </div>
            );
        }

        return (
            <div className="mt-4 mb-5">
                <h4>Write a Review</h4>
                {reviewError && <div className="alert alert-danger">{reviewError}</div>}
                <form onSubmit={handleSubmitReview}>
                    {renderStarRating()}
                    <div className="mb-3">
                        <label htmlFor="reviewText" className="form-label">Your Review:</label>
                        <textarea
                            id="reviewText"
                            className="form-control"
                            rows={4}
                            value={reviewText}
                            onChange={(e) => setReviewText(e.target.value)}
                            required
                        />
                    </div>
                    <button
                        type="submit"
                        className="btn btn-primary"
                        disabled={isSubmitting}
                    >
                        {isSubmitting ? "Submitting..." : "Submit Review"}
                    </button>
                </form>
            </div>
        );
    };
    
    return (
        <div className="container mt-4">
            <div className="row">
                {/* Images */}
                <div className="col-md-5">
                    {product.productImagePOJOS && product.productImagePOJOS.length > 0 ? (
                        <div id="productImagesCarousel" className="carousel slide" data-bs-ride="carousel">
                            <div className="carousel-inner">
                                {product.productImagePOJOS.map((image, index) => (
                                    <div key={image.id} className={`carousel-item ${index === 0 ? "active" : ""}`}>
                                        <img src={image.imageUrl} className="d-block w-100" alt={image.altText} />
                                    </div>
                                ))}
                            </div>
                            <button className="carousel-control-prev" type="button" data-bs-target="#productImagesCarousel" data-bs-slide="prev">
                                <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span className="visually-hidden">Previous</span>
                            </button>
                            <button className="carousel-control-next" type="button" data-bs-target="#productImagesCarousel" data-bs-slide="next">
                                <span className="carousel-control-next-icon" aria-hidden="true"></span>
                                <span className="visually-hidden">Next</span>
                            </button>
                        </div>
                    ) : (
                        <p>No images available.</p>
                    )}
                </div>

                {/* Details */}
                <div className="col-md-7">
                    <h2>{product.productName}</h2>
                    <div>
                        <strong>Average Rating:</strong> {averageRating.toFixed(1)} ⭐
                    </div>
                    <p className="text-muted">{product.categoryPOJO?.categoryName}</p>
                    <h4 className="text-primary">${product.price}</h4>
                    <p>{product.description}</p>                    

                    <p><strong>Stock:</strong> {product.stock > 0 ? `${product.stock} available` : "Out of stock"}</p>
                    <button className="btn btn-success">Add to Cart</button>
                </div>
            </div>
            {/* Review Form */}
            {renderReviewForm()}

            {/* Reviews */}
            <div className="mt-5">
                <h3>Reviews</h3>
                {product.productReviewPOJOS && product.productReviewPOJOS.length > 0 ? (
                    product.productReviewPOJOS.map(review => (
                        <div key={review.reviewId} className="border p-3 mb-3 ">
                            <strong>User id: {review.userId}</strong>
                            <div>
                                <strong>Rating:</strong> {Array(review.rating).fill("★").join("")}
                                {Array(5 - review.rating).fill("☆").join("")}
                            </div>
                            <div className="text-break">{review.reviewText}</div>
                            <div className="text-muted mt-2">
                                {new Date(review.createdAt).toLocaleString()}
                            </div>
                        </div>
                    ))
                ) : (
                    <p>No reviews yet. Be the first to review this product!</p>
                )}
            </div>

        </div>
    );
};

export default ProductDetail;
