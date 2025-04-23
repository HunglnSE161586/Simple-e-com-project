
import { useEffect,useState } from "react";
import ComplexNavbar from "./components/Navbar"
import Footer from "./components/Footer";
import HeroSection from "./components/Hero";
import FeatureSection from "./components/Feature";
import CategoryMenu from "./components/CategoryMenu";
import { fetchAllProducts } from "./api/ProductAPI";
import { Product } from "./types/Product";
//import { products } from "./dummy/DummyProductList"; 
import { categories } from "./dummy/DummyCategories";
import './App.css'

function App() {  
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadProducts = async () => {
      try {
        const data = await fetchAllProducts();
        setProducts(data);
      } catch (err) {
        console.error("Failed to fetch products:", err);
      } finally {
        setLoading(false);
      }
    };

    loadProducts();
  }, []);

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
