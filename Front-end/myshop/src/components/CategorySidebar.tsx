import { useEffect, useState } from "react";
import { Category } from "../types/Category";
import { fetchPagedCategories } from "../api/CategoryAPI";

interface Props {
  onSelectCategory: (categoryId: number | null) => void;
}

const CategorySidebar: React.FC<Props> = ({ onSelectCategory }) => {
  const [categories, setCategories] = useState<Category[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadCategories = async () => {
      try {
        const data = await fetchPagedCategories(0,15);
        setCategories(data.content);
      } catch (error) {
        console.error("Failed to load categories", error);
      } finally {
        setLoading(false);
      }
    };

    loadCategories();
  }, []);

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
          key={category.categoryId}
          className="list-group-item list-group-item-action"
          onClick={() => onSelectCategory(category.categoryId)}
        >
          {category.categoryName}
        </button>
      ))}
    </div>
  );
};

export default CategorySidebar;
