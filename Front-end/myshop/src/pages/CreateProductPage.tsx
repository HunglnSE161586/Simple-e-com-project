import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Sidebar from '../components/Sidebar';
import Topbar from '../components/Topbar';
import { fetchAllCategories } from '../api/CategoryAPI';
import { createProduct } from '../api/ProductAPI';
import { Category } from '../types/Category';
import { CreateProduct } from '../types/Product';
import { toast } from 'react-toastify';
import { CreateProductImage } from '../types/ProductImage';

const CreateProductPage: React.FC = () => {
  const [categories, setCategories] = useState<Category[]>([]);
  const [formData, setFormData] = useState<Omit<CreateProduct, "productImages">>({
    productName: '',
    description: '',
    price: 0,
    stock: 0,
    categoryId: 0
  });

  const [productImages, setProductImages] = useState<CreateProductImage[]>([
    { imageUrl: '', altText: '', displayOrder: 1 }
  ]);

  const navigate = useNavigate();

  useEffect(() => {
    const loadCategories = async () => {
      const data = await fetchAllCategories();
      setCategories(data);
    };
    loadCategories();
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: name === 'price' || name === 'stock' || name === 'categoryId' ? Number(value) : value,
    }));
  };

  const handleImageChange = (index: number, field: keyof CreateProductImage, value: string | number) => {
    const updatedImages = [...productImages];
    if (field === 'displayOrder') {
        updatedImages[index][field] = Number(value) as CreateProductImage['displayOrder'];
      } else {
        updatedImages[index][field] = value as string;
      }
    setProductImages(updatedImages);
  };

  const addImage = () => {
    const nextOrder = productImages.length + 1;
    setProductImages([...productImages, { imageUrl: '', altText: '', displayOrder: nextOrder }]);
  };

  const removeImage = (index: number) => {
    const updated = productImages.filter((_, i) => i !== index);
    const reordered = updated.map((img, i) => ({ ...img, displayOrder: i + 1 }));
    setProductImages(reordered);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const payload: CreateProduct = {
        ...formData,
        productImages: productImages.map((img, index) => ({
          ...img,
          displayOrder: index + 1,
        }))
      };
      await createProduct(payload);
      toast.success("Product created successfully");
      navigate('/dashboard/products');
    } catch (error) {
      toast.error("Failed to create product");
    }
  };

  return (
    <div className="d-flex">
      <Sidebar />
      <div className="flex-grow-1">
        <Topbar />
        <div className="container mt-4">
          <h2>Create New Product</h2>
          <form className="row g-3 mt-3" onSubmit={handleSubmit}>
            <div className="col-md-6">
              <label className="form-label">Product Name</label>
              <input type="text" name="productName" className="form-control" value={formData.productName} onChange={handleChange} />
            </div>
            <div className="col-md-6">
              <label className="form-label">Category</label>
              <select name="categoryId" className="form-select" value={formData.categoryId} onChange={handleChange}>
                <option value={0}>Select a category</option>
                {categories.map(cat => (
                  <option key={cat.id} value={cat.id}>{cat.categoryName}</option>
                ))}
              </select>
            </div>
            <div className="col-12">
              <label className="form-label">Description</label>
              <textarea name="description" className="form-control" rows={4} value={formData.description} onChange={handleChange}></textarea>
            </div>
            <div className="col-md-6">
              <label className="form-label">Price</label>
              <input type="number" name="price" className="form-control" value={formData.price} onChange={handleChange} />
            </div>
            <div className="col-md-6">
              <label className="form-label">Stock</label>
              <input type="number" name="stock" className="form-control" value={formData.stock} onChange={handleChange} />
            </div>

            <div className="col-12">
              <h5>Product Images</h5>
            </div>
            {productImages.map((img, index) => (
              <div className="row g-3 mb-2" key={index}>
                <div className="col-md-5">
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Image URL"
                    value={img.imageUrl}
                    onChange={e => handleImageChange(index, 'imageUrl', e.target.value)}
                  />
                </div>
                <div className="col-md-4">
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Alt text"
                    value={img.altText}
                    onChange={e => handleImageChange(index, 'altText', e.target.value)}
                  />
                </div>
                <div className="col-md-2 d-flex align-items-center">
                  <span className="form-text">Order: {index + 1}</span>
                </div>
                <div className="col-md-1 d-flex align-items-center">
                  {index > 0 && (
                    <button type="button" className="btn btn-outline-danger btn-sm" onClick={() => removeImage(index)}>
                      &times;
                    </button>
                  )}
                </div>
              </div>
            ))}
            <div className="col-12">
              <button type="button" className="btn btn-outline-secondary" onClick={addImage}>
                + Add Image
              </button>
            </div>
            <div className="col-12 mt-3">
              <button type="submit" className="btn btn-primary">Create Product</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default CreateProductPage;
