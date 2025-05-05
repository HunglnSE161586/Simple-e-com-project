import React, { useEffect, useState } from 'react';
import Sidebar from '../components/Sidebar';
import Topbar from '../components/Topbar';
import { Product } from '../types/Product';
import { fetchPagedProduct, softDeleteProduct, softRestoreProduct } from '../api/ProductAPI';
import { PaginatedResponse } from '../types/PaginatedResponse';
import { toast } from 'react-toastify';

const ProductManagement: React.FC = () => {
    const [products, setProducts] = useState<Product[]>([]);
    const [page, setPage] = useState(0);
    const [size] = useState(10);
    const [totalPages, setTotalPages] = useState(1);

    const loadProducts = async () => {
        try {
            const response: PaginatedResponse<Product> = await fetchPagedProduct(page, size);
            setProducts(response.content);
            setTotalPages(response.totalPages);
        } catch (error) {
            console.error('Error loading products:', error);
        }
    };

    useEffect(() => {
        loadProducts();
    }, [page]);
    const handleDelete = async (productId: number) => {
        const confirmDelete = window.confirm("Are you sure you want to delete this product?");
        if (confirmDelete) {
            try {
                await softDeleteProduct(productId);
                loadProducts();
                toast.success("Delete success!");
            } catch (error) {
                console.error("Failed to delete product:", error);
                toast.error("Failed to delete product:" + error);
            }
        }
    };

    const handleRestore = async (productId: number) => {
        const confirmRestore = window.confirm("Are you sure you want to restore this product?");
        if (confirmRestore) {
            try {
                await softRestoreProduct(productId);
                loadProducts();
                toast.success("Restore success!");
            } catch (error) {
                console.error("Failed to restore product:", error);
                toast.error("Failed to restore product:" + error);
            }
        }
    };


    const handlePrevious = () => {
        if (page > 0) setPage(prev => prev - 1);
    };

    const handleNext = () => {
        if (page < totalPages - 1) setPage(prev => prev + 1);
    };

    return (
        <div className="d-flex">
            <Sidebar />
            <div className="flex-grow-1">
                <Topbar />
                <div className="container mt-4">
                    <h2 className="text-center mb-4">Product Management</h2>
                    <div className="table-responsive">
                        <table className="table table-bordered table-hover">
                            <thead className="table-light">
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Price</th>
                                    <th>Stock</th>
                                    <th>Featured</th>
                                    <th>Status</th>
                                    <th>Created</th>
                                    <th>Updated</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                {products.map(product => (
                                    <tr key={product.productId}>
                                        <td>{product.productId}</td>
                                        <td>{product.productName}</td>
                                        <td>${product.price.toFixed(2)}</td>
                                        <td>{product.stock}</td>
                                        <td>
                                            {product.isFeatured ? (
                                                <span className="badge bg-info">Yes</span>
                                            ) : (
                                                <span className="badge bg-secondary">No</span>
                                            )}
                                        </td>
                                        <td>
                                            {product.isActive ? (
                                                <span className="badge bg-success">Active</span>
                                            ) : (
                                                <span className="badge bg-danger">Inactive</span>
                                            )}
                                        </td>
                                        <td>{new Date(product.createdAt).toLocaleString()}</td>
                                        <td>{new Date(product.updatedAt).toLocaleString()}</td>
                                        <td>
                                            <button
                                                className="btn btn-sm btn-success"                                                
                                            >
                                                Edit
                                            </button>
                                        </td>
                                        <td>

                                            {product.isActive ? (
                                                <button
                                                    className="btn btn-sm btn-danger"
                                                    onClick={() => handleDelete(product.productId)}
                                                >
                                                    Delete
                                                </button>
                                            ) : (
                                                <button
                                                    className="btn btn-sm btn-success"
                                                    onClick={() => handleRestore(product.productId)}
                                                >
                                                    Restore
                                                </button>
                                            )}
                                        </td>                                        
                                    </tr>
                                ))}
                                {products.length === 0 && (
                                    <tr>
                                        <td colSpan={7} className="text-center">No products found.</td>
                                    </tr>
                                )}
                            </tbody>
                        </table>
                    </div>
                    <div className="d-flex justify-content-between align-items-center mt-3">
                        <button className="btn btn-outline-primary" onClick={handlePrevious} disabled={page === 0}>
                            Previous
                        </button>
                        <span>Page {page + 1} of {totalPages}</span>
                        <button className="btn btn-outline-primary" onClick={handleNext} disabled={page >= totalPages - 1}>
                            Next
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ProductManagement;
