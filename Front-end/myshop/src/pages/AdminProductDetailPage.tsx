import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { fetchProductDetail, updateProduct } from '../api/ProductAPI';
import { ProductDetail, UpdateProduct } from '../types/Product';
import Sidebar from '../components/Sidebar';
import Topbar from '../components/Topbar';
import { toast } from 'react-toastify';
import { fetchAllCategories } from '../api/CategoryAPI';
import { Category } from '../types/Category';
import { ProductImage, UpdateProductImage } from '../types/ProductImage';
import { updateProductImage } from '../api/ProductImageAPI';




export default function AdminProductDetailPage() {
  const { id } = useParams<{ id: string }>();
  const [product, setProduct] = useState<ProductDetail | null>(null);
  const [loading, setLoading] = useState(true);
  const [categories, setCategories] = useState<Category[]>([]);
  const [selectedCategoryId, setSelectedCategoryId] = useState<number | null>(null);
  const [selectedImage, setSelectedImage] = useState<ProductImage | null>(null);
  const [imageFormData, setImageFormData] = useState<UpdateProductImage>({ imageUrl: "", altText: "" });
  const [showImageModal, setShowImageModal] = useState(false);
  const [formData, setFormData] = useState<UpdateProduct>({
    productName: '',
    description: '',
    price: 0,
    stock: 0,
    categoryId: 0,
    isFeatured: false,
  });

  useEffect(() => {
    if (!id) return;

    const loadData = async () => {
      try {
        const [productData, allCategories] = await Promise.all([
          fetchProductDetail(Number(id)),
          fetchAllCategories(),
        ]);

        setProduct(productData);
        setFormData({
          productName: productData.productName,
          description: productData.description,
          price: productData.price,
          stock: productData.stock,
          categoryId: productData.categoryPOJO.id,
          isFeatured: productData.isFeatured,
        });
        setCategories(allCategories);
        setSelectedCategoryId(productData.categoryPOJO.id);
      } catch (error) {
        console.error("Failed to load data", error);
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, [id]);


  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value, type } = e.target;

    const newValue = type === 'checkbox'
      ? (e.target as HTMLInputElement).checked // type narrowing for checkbox
      : value;

    setFormData(prev => ({
      ...prev,
      [name]: newValue,
    }));
  };


  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!id) return;

    try {
      await updateProduct(Number(id), {
        ...formData,
        price: Number(formData.price),
        stock: Number(formData.stock),
        categoryId: selectedCategoryId as number,
      });
      toast.success('Product updated successfully');
    } catch (error) {
      toast.error('Failed to update product');
    }
  };
  const handleImageUpdate = async () => {
    if (!selectedImage) return;
    try {
      await updateProductImage(selectedImage.id, imageFormData);
      toast.success("Image updated successfully");
      setShowImageModal(false);

      // Refresh product detail
      const updated = await fetchProductDetail(Number(id));
      setProduct(updated);
    } catch (error) {
      toast.error("Failed to update image");
    }
  };


  const handleImageEditClick = (img: ProductImage) => {
    setSelectedImage(img);
    setImageFormData({ imageUrl: img.imageUrl, altText: img.altText });
    setShowImageModal(true);
  };

  const handleImageFormChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setImageFormData(prev => ({ ...prev, [name]: value }));
  };

  if (loading) return <div className="container mt-5">Loading...</div>;
  if (!product) return <div className="container mt-5 text-danger">Not found.</div>;

  return (
    <div className="d-flex">
      <Sidebar />
      <div className="flex-grow-1">
        <Topbar />
        <div className="container mt-4">
          <h2>Edit Product: {product.productName}</h2>
          <form className="row g-3 mt-3" onSubmit={handleSubmit}>
            <div className="col-md-6">
              <label className="form-label">Product Name</label>
              <input
                type="text"
                name="productName"
                className="form-control"
                value={formData.productName}
                onChange={handleChange}
              />
            </div>
            <div className="col-md-6">
              <label className="form-label">Category</label>
              <select
                className="form-select"
                value={selectedCategoryId ?? ''}
                onChange={(e) => setSelectedCategoryId(Number(e.target.value))}
              >
                <option value="" disabled>Select a category</option>
                {categories.map((cat) => (
                  <option key={cat.id} value={cat.id}>
                    {cat.categoryName}
                  </option>
                ))}
              </select>
            </div>
            <div className="col-md-12">
              <label className="form-label">Description</label>
              <textarea
                name="description"
                className="form-control"
                rows={7}
                value={formData.description}
                onChange={handleChange}
              ></textarea>
            </div>
            <div className="col-md-4">
              <label className="form-label">Price</label>
              <input
                type="number"
                name="price"
                step="0.01"
                className="form-control"
                value={formData.price}
                onChange={handleChange}
              />
            </div>
            <div className="col-md-4">
              <label className="form-label">Stock</label>
              <input
                type="number"
                name="stock"
                className="form-control"
                value={formData.stock}
                onChange={handleChange}
              />
            </div>
            <div className="col-md-4 d-flex align-items-center">
              <div className="form-check me-3">
                <input
                  className="form-check-input"
                  type="checkbox"
                  name="isFeatured"
                  checked={formData.isFeatured}
                  onChange={handleChange}
                />
                <label className="form-check-label">Featured</label>
              </div>
            </div>
            <div className="col-12">
              <button type="submit" className="btn btn-primary">Save Changes</button>
            </div>
          </form>

          <hr className="my-5" />

          <h4>Product Images</h4>
          <table className="table table-bordered mt-3">
            <thead className="table-light">
              <tr>
                <th>#</th>
                <th>Image URL</th>
                <th>Alt Text</th>
                <th>Display Order</th>
                <th>Created At</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {product.productImagePOJOS.map((img, idx) => (
                <tr key={img.id}>
                  <td>{idx + 1}</td>
                  <td>
                    <a href={img.imageUrl} target="_blank" rel="noopener noreferrer">
                      View
                    </a>
                  </td>
                  <td>{img.altText}</td>
                  <td>{img.displayOrder}</td>
                  <td>{new Date(img.createdAt).toLocaleString()}</td>
                  <td>
                    <button className="btn btn-sm btn-success" onClick={() => handleImageEditClick(img)}>Edit</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          {showImageModal && (
            <div className="modal show d-block" tabIndex={-1} role="dialog">
              <div className="modal-dialog" role="document">
                <div className="modal-content">
                  <div className="modal-header">
                    <h5 className="modal-title">Edit Product Image</h5>
                    <button type="button" className="btn-close" onClick={() => setShowImageModal(false)}></button>
                  </div>
                  <div className="modal-body">
                    <div className="mb-3">
                      <label className="form-label">Image URL</label>
                      <input
                        type="text"
                        name="imageUrl"
                        className="form-control"
                        value={imageFormData.imageUrl}
                        onChange={handleImageFormChange}
                      />
                    </div>
                    <div className="mb-3">
                      <label className="form-label">Alt Text</label>
                      <input
                        type="text"
                        name="altText"
                        className="form-control"
                        value={imageFormData.altText}
                        onChange={handleImageFormChange}
                      />
                    </div>
                  </div>
                  <div className="modal-footer">
                    <button type="button" className="btn btn-secondary" onClick={() => setShowImageModal(false)}>Cancel</button>
                    <button type="button" className="btn btn-primary" onClick={handleImageUpdate}>Save changes</button>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
