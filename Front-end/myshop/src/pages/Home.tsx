import { useEffect,useState } from "react";
import HeroSection from "../components/Hero";
import FeatureSection from "../components/Feature";
import CategoryMenu from "../components/CategoryMenu";
import { fetchPagedProduct } from "../api/ProductAPI";
import { Product } from "../types/Product";

import { categories } from "../dummy/DummyCategories";

export function Home(){
    const [products, setProducts] = useState<Product[]>([]);
  const [page, setPage] = useState(0);  
  const [loading, setLoading] = useState(true);
  


  useEffect(() => {
    const loadProducts = async () => {
      setLoading(true);
      try {
        const data = await fetchPagedProduct(0, 6); 
        setProducts(data.content);        
      } catch (err) {
        console.error("Failed to fetch paged products:", err);
      } finally {
        setLoading(false);
      }
    };

    loadProducts();
  }, [page]);
  
  // const handleNext = () => setPage((p) => Math.min(p + 1, totalPages - 1));
  // const handlePrevious = () => setPage((p) => Math.max(p - 1, 0));

  return (
    <>
      <div className="App">          
          <HeroSection/>
          <CategoryMenu categories={categories} />
          {loading ? <p>Loading products...</p> : <FeatureSection products={products} />}          
        </div>
    </>
  )
}
export default Home