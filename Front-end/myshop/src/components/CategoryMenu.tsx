import { Link } from "react-router-dom";
import { Category } from "../types/Category";
import { useId } from "react";

interface CategoryMenuProps {
  categories: Category[];
}

const chunkArray = (arr: Category[], size: number): Category[][] => {
  const result: Category[][] = [];
  for (let i = 0; i < arr.length; i += size) {
    result.push(arr.slice(i, i + size));
  }
  return result;
};

const CategoryMenu = ({ categories }: CategoryMenuProps) => {
  const carouselId = useId(); // Unique id for Bootstrap carousel
  const slides = chunkArray(categories, 3); // Split into groups of 3

  return (
    <section className="container my-5">
      <h2 className="text-center mb-4">Shop by Category</h2>

      <div id={carouselId} className="carousel slide" data-bs-ride="carousel">
        <div className="carousel-inner">
          {slides.map((group, idx) => (
            <div key={idx} className={`carousel-item ${idx === 0 ? "active" : ""}`}>
              <div className="row g-4">
                {group.map((category) => (
                  <div key={category.id} className="col-md-4">
                    <div className="card h-100 border-0 shadow-sm text-center">
                      <img
                        src={category.image}
                        className="card-img-top"
                        alt={category.categoryName}
                        style={{ height: "20rem", objectFit: "cover" }}
                      />
                      <div className="card-body">
                        <h5 className="card-title">{category.categoryName}</h5>
                        <Link
                          to="/products"
                          state={{ categoryId: category.id }}
                          className="btn btn-outline-primary"
                        >
                          Explore {category.categoryName}
                        </Link>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </div>

        {/* Carousel Controls */}
        {slides.length > 1 && (
          <>
            <button
              className="carousel-control-prev"
              type="button"
              data-bs-target={`#${carouselId}`}
              data-bs-slide="prev"
            >
              <span className="carousel-control-prev-icon" aria-hidden="true" />
              <span className="visually-hidden">Previous</span>
            </button>
            <button
              className="carousel-control-next"
              type="button"
              data-bs-target={`#${carouselId}`}
              data-bs-slide="next"
            >
              <span className="carousel-control-next-icon" aria-hidden="true" />
              <span className="visually-hidden">Next</span>
            </button>
          </>
        )}
      </div>
    </section>
  );
};

export default CategoryMenu;
