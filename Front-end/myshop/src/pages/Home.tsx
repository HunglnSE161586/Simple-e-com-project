import { useEffect, useState } from "react";
import HeroSection from "../components/Hero";
import FeatureSection from "../components/Feature";
import CategoryMenu from "../components/CategoryMenu";
import { fetchPagedProductIsFeature } from "../api/ProductAPI";
import { Product } from "../types/Product";

import { Category } from "../types/Category";
import { fetchPagedCategories } from "../api/CategoryAPI";

export function Home() {
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [categories, setCategories] = useState<Category[]>([]);


  useEffect(() => {
    const loadProducts = async () => {
      setLoading(true);
      try {
        const data = await fetchPagedProductIsFeature(0, 6, true);
        setProducts(data.content);
      } catch (err) {
        console.error("Failed to fetch paged products:", err);
      } finally {
        setLoading(false);
      }
    };
    const loadCategories = async () => {
      try {
        const data = await fetchPagedCategories(0, 9);
        setCategories(data.content);
      } catch (err) {

      }
    }
    loadCategories();
    loadProducts();
  }, []);

  // const handleNext = () => setPage((p) => Math.min(p + 1, totalPages - 1));
  // const handlePrevious = () => setPage((p) => Math.max(p - 1, 0));

  return (
    <>
      <div className="App">
        <HeroSection />
        <CategoryMenu categories={categories} />
        {loading ? <p>Loading products...</p> : <FeatureSection products={products} />}
      </div>
    </>
  )
}
export default Home