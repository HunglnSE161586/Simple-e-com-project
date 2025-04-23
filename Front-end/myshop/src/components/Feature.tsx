
import ProductList from "./ProductList";
import { Product } from "../types/Product";
interface FeatureSectionProps {
    products: Product[];
  }

const FeatureSection = ({ products}:FeatureSectionProps) => {    
  return (
    <section className="container my-5">
      <h2 className="text-center mb-4">Featured Products</h2>
      <ProductList products={products} />
    </section>
  );
};

export default FeatureSection;

