
import ProductCard from './ProductCard';
import { Product } from "../types/Product";
interface ProductList{
    products:Product[];
}
const ProductList = ({ products }:ProductList) => {
    if (!products || products.length === 0) {
      return <p>No products available.</p>;  // Handle empty state
    }
    return (
      <div className="row g-4">
        {products.map((product) => (
          <div className="col-md-4" key={product.productId}>
            <ProductCard product={product} />
          </div>
        ))}
      </div>
    );
  };

export default ProductList;
