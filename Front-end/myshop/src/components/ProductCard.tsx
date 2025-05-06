import { Link } from "react-router-dom";
import { Product } from "../types/Product";

interface ProductCardProps {
  product: Product;
}

const ProductCard: React.FC<ProductCardProps> = ({ product }) => (
  <Link to={`/products/${product.id}`} className="text-decoration-none text-dark">
    <div className="card h-100">
      <img
        src={product.productImagePOJO?.imageUrl}
        className="card-img-top"
        alt={product.productImagePOJO?.altText || product.productName}
      />
      <div className="card-body d-flex flex-column">
        <h5 className="card-title">{product.productName}</h5>
        <p className="card-text flex-grow-1">{product.description}</p>
        <div className="d-flex justify-content-between align-items-center">
          <span className="text-primary fw-bold">${product.price}</span>
          <button className="btn btn-sm btn-outline-dark">Add to Cart</button>
        </div>
      </div>
    </div>
  </Link>
);
export default ProductCard;
