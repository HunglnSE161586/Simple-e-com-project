
import ComplexNavbar from "./components/Navbar"
import Footer from "./components/Footer";
import HeroSection from "./components/Hero";
import FeatureSection from "./components/Feature";
import CategoryMenu from "./components/CategoryMenu";
import { products } from "./dummy/DummyProductList"; 
import { categories } from "./dummy/DummyCategories";
import './App.css'

function App() {  

  return (
    <>
      <div className="App">
          <ComplexNavbar/>
          <HeroSection/>
          <CategoryMenu categories={categories} />
          <FeatureSection products={products} />
          <Footer/>                              
        </div>
    </>
  )
}

export default App
