import React, { useEffect, useState } from 'react';
import { fetchPagedProduct, fetchPagedProductByCategory } from '../api/ProductAPI';
import { Product } from '../types/Product';
import CategorySidebar from "../components/CategorySidebar";
import { PaginatedResponse } from '../types/PaginatedResponse';
import ProductCard from '../components/ProductCard';
import { useLocation } from "react-router-dom";

const ProductList: React.FC = () => {
    const [products, setProducts] = useState<Product[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string>('');
    const [page, setPage] = useState<number>(0);
    const location = useLocation();
    const categoryIdFromState = location.state?.categoryId as number | undefined;
    const [selectedCategoryId, setSelectedCategoryId] = useState<number | null>(
        categoryIdFromState ?? null
    );

    useEffect(() => {
        const loadProducts = async () => {
            try {
                let data: PaginatedResponse<Product>;
                if (selectedCategoryId) {
                    data = await fetchPagedProductByCategory(selectedCategoryId, page, 6);
                }
                else {
                    data = await fetchPagedProduct(page, 6);
                }
                setProducts(data.content);
            } catch (err) {
                setError('Failed to fetch products.');
            } finally {
                setLoading(false);
            }
        };

        loadProducts();
    }, [page, selectedCategoryId, categoryIdFromState]);

    if (loading) return <div className="text-center mt-5">Loading...</div>;
    if (error) return <div className="alert alert-danger text-center">{error}</div>;

    return (
        <div className="container mt-4">
            <div className="row">
                {/* Sidebar */}
                <div className="col-md-3">
                    <CategorySidebar onSelectCategory={(categoryId) => {
                        setSelectedCategoryId(categoryId);
                        setPage(0); //  Reset to first page
                    }} />
                </div>

                {/* Products */}
                <div className="col-md-9">
                    <h2>{selectedCategoryId ? "Filtered Products" : "All Products"}</h2>
                    <div className="row">
                        {products.length > 0 ? (
                            products.map(product => (
                                <div key={product.id} className="col-md-4 mb-4">
                                    <ProductCard product={product} />
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
