import { useEffect, useState } from "react";
import { Category } from "../types/Category";
import { fetchPagedCategories } from "../api/CategoryAPI";

interface Props {
  onSelectCategory: (categoryId: number | null) => void;
}
const PAGE_SIZE = 15;
const CategorySidebar: React.FC<Props> = ({ onSelectCategory }) => {
  const [categories, setCategories] = useState<Category[]>([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  useEffect(() => {
    const loadCategories = async (pageIndex: number) => {
      try {
        const data = await fetchPagedCategories(pageIndex,PAGE_SIZE);
        setCategories(data.content);
        setTotalPages(data.totalPages);
      } catch (error) {
        console.error("Failed to load categories", error);
      } finally {
        setLoading(false);
      }
    };

    loadCategories(page);
  }, [page]);

  if (loading) return <div>Loading categories...</div>;

  return (
    <div className="list-group">
      <button
        className="list-group-item list-group-item-action"
        onClick={() => onSelectCategory(null)}
      >
        All Products
      </button>
      {categories.map(category => (
        <button
          key={category.id}
          className="list-group-item list-group-item-action"
          onClick={() => onSelectCategory(category.id)}
        >
          {category.categoryName}
        </button>
      ))}
      {/* Pagination Controls */}
      <div className="d-flex justify-content-center align-items-center gap-2">
        <button
          className="btn btn-outline-primary btn-sm"
          disabled={page === 0}
          onClick={() => setPage((p) => p - 1)}
        >
          &laquo; Prev
        </button>

        <select
          className="form-select form-select-sm w-auto"
          value={page}
          onChange={(e) => setPage(Number(e.target.value))}
        >
          {Array.from({ length: totalPages }, (_, i) => (
            <option key={i} value={i}>
              Page {i + 1}
            </option>
          ))}
        </select>

        <button
          className="btn btn-outline-primary btn-sm"
          disabled={page >= totalPages - 1}
          onClick={() => setPage((p) => p + 1)}
        >
          Next &raquo;
        </button>
      </div>
    </div>
  );
};

export default CategorySidebar;
