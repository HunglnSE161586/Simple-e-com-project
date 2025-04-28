import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import type { ProductDetail } from "../types/Product";
import axiosInstance from "../api/AxiosInstance";

const ProductDetail: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [product, setProduct] = useState<ProductDetail | null>(null);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

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

    if (loading) return <div className="text-center mt-5">Loading...</div>;
    if (error) return <div className="alert alert-danger text-center">{error}</div>;
    if (!product) return <div className="text-center mt-5">Product not found.</div>;

    return (
        <div className="container mt-4">
            <div className="row">
                {/* Images */}
                <div className="col-md-5">
                    {product.productImagePOJOS && product.productImagePOJOS.length > 0 ? (
                        <div id="productImagesCarousel" className="carousel slide" data-bs-ride="carousel">
                            <div className="carousel-inner">
                                {product.productImagePOJOS.map((image, index) => (
                                    <div key={image.imageId} className={`carousel-item ${index === 0 ? "active" : ""}`}>
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
                    <p className="text-muted">{product.categoryPOJO?.categoryName}</p>
                    <h4 className="text-primary">${product.price}</h4>
                    <p>{product.description}</p>
                    <p>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
                    </p>

                    <p><strong>Stock:</strong> {product.stock > 0 ? `${product.stock} available` : "Out of stock"}</p>
                    <button className="btn btn-success">Add to Cart</button>
                </div>
            </div>

            {/* Reviews */}
            {product.productReviewPOJOS && product.productReviewPOJOS.length > 0 ? (
                <div className="mt-5">
                    <h3>Reviews</h3>
                    {product.productReviewPOJOS.map(review => (
                        <div key={review.reviewId} className="border p-3 mb-3">
                            <div><strong>Rating:</strong> {review.rating} ⭐</div>
                            <div>{review.reviewText}</div>
                        </div>
                    ))}
                </div>
            ):(
                <div className="mt-5">
                    <h3>Reviews</h3>
                    <p>No reviews yet.</p>
                </div>
            )}
            
        </div>
    );
};

export default ProductDetail;
