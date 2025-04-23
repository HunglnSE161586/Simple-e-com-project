
import { useEffect,useState } from "react";
import ComplexNavbar from "./components/Navbar"
import Footer from "./components/Footer";
import HeroSection from "./components/Hero";
import FeatureSection from "./components/Feature";
import CategoryMenu from "./components/CategoryMenu";
import { fetchPagedProduct } from "./api/ProductAPI";
import { Product } from "./types/Product";
//import { products } from "./dummy/DummyProductList"; 
import { categories } from "./dummy/DummyCategories";
import './App.css'

function App() {  
  const [products, setProducts] = useState<Product[]>([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadProducts = async () => {
      setLoading(true);
      try {
        const data = await fetchPagedProduct(page, 6); // for example: 6 products per page
        setProducts(data.content);
        setTotalPages(data.totalPages);
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
          <ComplexNavbar/>
          <HeroSection/>
          <CategoryMenu categories={categories} />
          {loading ? <p>Loading products...</p> : <FeatureSection products={products} />}
          <Footer/>                              
        </div>
    </>
  )
}

export default App
