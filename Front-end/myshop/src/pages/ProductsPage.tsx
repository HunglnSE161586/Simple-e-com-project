import React, { useEffect, useState } from 'react';
import { fetchPagedProduct } from '../api/ProductAPI';
import { Product } from '../types/Product';
import CategorySidebar from "../components/CategorySidebar";
const ProductList: React.FC = () => {
    const [products, setProducts] = useState<Product[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string>('');
    const [page, setPage] = useState<number>(0);
    const [selectedCategoryId, setSelectedCategoryId] = useState<number | null>(null);
    useEffect(() => {
        const loadProducts = async () => {
            try {
                const data = await fetchPagedProduct(page, 6); // page 0, size 6
                setProducts(data.content);
            } catch (err) {
                setError('Failed to fetch products.');
            } finally {
                setLoading(false);
            }
        };

        loadProducts();
    }, [page]);

    if (loading) return <div className="text-center mt-5">Loading...</div>;
    if (error) return <div className="alert alert-danger text-center">{error}</div>;

    return (
        <div className="container mt-4">
            <div className="row">
                {/* Sidebar */}
                <div className="col-md-3">
                    <CategorySidebar onSelectCategory={setSelectedCategoryId} />
                </div>

                {/* Products */}
                <div className="col-md-9">
                    <h2>{selectedCategoryId ? "Filtered Products" : "All Products"}</h2>
                    <div className="row">
                        {products.length > 0 ? (
                            products.map(product => (
                                <div key={product.productId} className="col-md-4 mb-4">
                                    <div className="card h-100">
                                        <img src={product.productImagePOJO?.imageUrl} className="card-img-top" alt={product.productImagePOJO?.altText} />
                                        <div className="card-body d-flex flex-column">
                                            <h5 className="card-title">{product.productName}</h5>
                                            <p className="card-text">${product.price}</p>
                                            <a href="#" className="btn btn-primary mt-auto">View Details</a>
                                        </div>
                                    </div>
                                </div>
                            ))
                        ) : (
                            <div>No products found.</div>
                        )}
                    </div>
                </div>
            </div>
            <div className="d-flex justify-content-center mt-4">
                <button
                    className="btn btn-outline-primary me-2"
                    onClick={() => setPage(prev => Math.max(prev - 1, 0))}
                    disabled={page === 0}
                >
                    Previous
                </button>
                <button
                    className="btn btn-outline-primary"
                    onClick={() => setPage(prev => prev + 1)}
                >
                    Next
                </button>
            </div>
        </div>
    );
};

export default ProductList;
