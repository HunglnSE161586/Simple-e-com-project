import { Category } from "../types/Category";
interface CategoryMenu{
    categories:Category[]
}

const CategoryMenu = ({ categories }:CategoryMenu) => {
  return (
    <section className="container my-5">
      <h2 className="text-center mb-4">Shop by Category</h2>
      <div className="row g-4">
        {categories.map((category) => (
          <div key={category.id} className="col-md-4">
            <div className="card h-100 border-0 shadow-sm text-center">
              <img
                src={category.image}
                className="card-img-top"
                alt={category.name}
                style={{ height: "20rem", objectFit: "cover" }}
              />
              <div className="card-body">
                <h5 className="card-title">{category.name}</h5>
                <a href="#" className="btn btn-outline-primary">
                  Explore {category.name}
                </a>
              </div>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
};

export default CategoryMenu;
