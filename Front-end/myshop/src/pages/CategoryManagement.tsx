import React, { useEffect, useState } from "react";
import Sidebar from "../components/Sidebar";
import Topbar from "../components/Topbar";
import {
  fetchPagedCategories,
  createCategory,
  updateCategory,
} from "../api/CategoryAPI";
import { Category, CreateCategory } from "../types/Category";
import { PaginatedResponse } from "../types/PaginatedResponse";
import { toast } from "react-toastify";

const CategoryManagement: React.FC = () => {
  const [categories, setCategories] = useState<Category[]>([]);
  const [page, setPage] = useState(0);
  const [size] = useState(10);
  const [totalPages, setTotalPages] = useState(1);
  const [modalCategory, setModalCategory] = useState<CreateCategory>({
    categoryName: "",
    description: "",
    image: "",
  });
  const [editingId, setEditingId] = useState<number | null>(null);
  const [showModal, setShowModal] = useState(false);

  const fetchCategories = async () => {
    try {
      const response: PaginatedResponse<Category> = await fetchPagedCategories(
        page,
        size
      );
      setCategories(response.content);
      setTotalPages(response.totalPages);
    } catch (error) {
      console.error("Failed to fetch categories:", error);
    }
  };

  useEffect(() => {
    fetchCategories();
  }, [page]);

  const handleSubmit = async () => {
    try {
      if (editingId !== null) {
        await updateCategory(editingId, modalCategory);
        toast.success("Update Successful");
      } else {
        await createCategory(modalCategory);
        toast.success("Create Successful");
      }
      setModalCategory({ categoryName: "", description: "", image: "" });
      setEditingId(null);
      setShowModal(false);
      fetchCategories();
    } catch (error) {
      console.error("Error saving category:", error);
    }
  };

  const handleEdit = (category: Category) => {
    setEditingId(category.categoryId);
    setModalCategory({
      categoryName: category.categoryName,
      description: category.description,
      image: category.image,
    });
    setShowModal(true);
  };

  const handlePrevious = () => page > 0 && setPage(prev => prev - 1);
  const handleNext = () => page < totalPages - 1 && setPage(prev => prev + 1);

  return (
    <div className="d-flex">
      <Sidebar />
      <div className="flex-grow-1">
        <Topbar />
        <div className="p-4">
          <h2 className="text-center mb-4">Category Management</h2>

          <button className="btn btn-success mb-3" onClick={() => { setShowModal(true); setEditingId(null); }}>
            + Add Category
          </button>

          <div className="table-responsive">
            <table className="table table-bordered table-hover">
              <thead className="table-light">
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Description</th>
                  <th>Image</th>
                  <th>Created At</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {categories.map((cat) => (
                  <tr key={cat.categoryId}>
                    <td>{cat.categoryId}</td>
                    <td>{cat.categoryName}</td>
                    <td>{cat.description}</td>
                    <td>
                      <img src={cat.image} alt="category" width={50} height={50} />
                    </td>
                    <td>{new Date(cat.createdAt).toLocaleString()}</td>
                    <td>
                      <button
                        className="btn btn-sm btn-primary"
                        onClick={() => handleEdit(cat)}
                      >
                        Edit
                      </button>
                    </td>
                  </tr>
                ))}
                {categories.length === 0 && (
                  <tr>
                    <td colSpan={6} className="text-center">
                      No categories found.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>

          <div className="d-flex justify-content-between align-items-center mt-3">
            <button
              className="btn btn-outline-primary"
              onClick={handlePrevious}
              disabled={page === 0}
            >
              Previous
            </button>
            <span>
              Page {page + 1} of {totalPages}
            </span>
            <button
              className="btn btn-outline-primary"
              onClick={handleNext}
              disabled={page >= totalPages - 1}
            >
              Next
            </button>
          </div>
        </div>
      </div>

      {/* Modal */}
      {showModal && (
        <div className="modal d-block" tabIndex={-1}>
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">
                  {editingId ? "Edit Category" : "Add Category"}
                </h5>
                <button
                  type="button"
                  className="btn-close"
                  onClick={() => setShowModal(false)}
                ></button>
              </div>
              <div className="modal-body">
                <div className="mb-3">
                  <label>Name</label>
                  <input
                    type="text"
                    className="form-control"
                    value={modalCategory.categoryName}
                    onChange={(e) =>
                      setModalCategory((prev) => ({
                        ...prev,
                        categoryName: e.target.value,
                      }))
                    }
                  />
                </div>
                <div className="mb-3">
                  <label>Description</label>
                  <textarea
                    className="form-control"
                    rows={3}
                    value={modalCategory.description}
                    onChange={(e) =>
                      setModalCategory((prev) => ({
                        ...prev,
                        description: e.target.value,
                      }))
                    }
                  />
                </div>
                <div className="mb-3">
                  <label>Image URL</label>
                  <input
                    type="text"
                    className="form-control"
                    value={modalCategory.image}
                    onChange={(e) =>
                      setModalCategory((prev) => ({
                        ...prev,
                        image: e.target.value,
                      }))
                    }
                  />
                </div>
              </div>
              <div className="modal-footer">
                <button
                  className="btn btn-secondary"
                  onClick={() => setShowModal(false)}
                >
                  Cancel
                </button>
                <button className="btn btn-primary" onClick={handleSubmit}>
                  Save
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default CategoryManagement;
