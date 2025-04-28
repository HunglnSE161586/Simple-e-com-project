import { Product } from "../types/Product";

const ProductCard = ({ productImagePOJO, productName, description, price }:Omit<Product,"productId">) => (
  <div className="card h-100">
    <img src={productImagePOJO?.imageUrl} className="card-img-top" alt={productName} />
    <div className="card-body d-flex flex-column">
      <h5 className="card-title">{productName}</h5>
      <p className="card-text flex-grow-1">{description}</p>
      <div className="d-flex justify-content-between align-items-center">
        <span className="text-primary fw-bold">${price}</span>
        <button className="btn btn-sm btn-outline-dark">Add to Cart</button>
      </div>
    </div>
  </div>
);

export default ProductCard;
