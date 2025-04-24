import { Link } from "react-router-dom";
import { useState } from "react";
const Navbar = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
      <div className="container mx-auto px-4 flex items-center   w-full">
        <div className="flex items-center justify-between md:justify-center">
          <Link to="/" className="text-sm font-medium">
            <img src="https://media.printables.com/media/prints/415756/images/3449559_b2fc84f0-f031-48f9-9cd9-3bc455ed5200/thumbs/inside/1280x960/png/qian-nian-xue-yuan-millennium.webp"
              className="navbar-logo"
              alt="logo"
            />
          </Link>

          <button
            className="md:hidden navbar-toggler"
            type="button"
            onClick={() => setIsMenuOpen(!isMenuOpen)} // Toggle the menu
            aria-expanded={isMenuOpen}
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon" />
          </button>
          <div className={`md:flex space-x-4  ${isMenuOpen ? "block" : "hidden"} md:flex`}>
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item">
                <Link className="nav-link active" aria-current="page" to="/" >
                  Home
                </Link>
              </li>
              <li className="nav-item">
                <a className="nav-link" href="#">
                  Products
                </a>
              </li>
              <li className="nav-item">
                <a className="nav-link" href="#">
                  Categories
                </a>
              </li>
            </ul>
          </div>
        </div>

        <div className={`md:flex space-x-4  ${isMenuOpen ? "block" : "hidden"} md:block`}>

          <form className="d-flex" role="search">
            <input
              className="form-control me-2 text-base"
              type="search"
              placeholder="Product name"
              aria-label="Search"
            />
            <Link to="/" className="text-sm font-medium">
              <button className="btn btn-outline-primary" type="submit">
                Search
              </button>
            </Link>
            <Link to="/" className="text-sm font-medium">
              <button type="button" className="btn btn-dark btn-icon .text-nowrap ms-3">
                <span className="btn-inner--icon me-1">
                  <i className="fa fa-github" aria-hidden="true"></i>
                </span>
                <span className="btn-inner--text" style={{ whiteSpace: "nowrap" }}>Sign in </span>
              </button>
            </Link>
            <Link to="/register" className="text-sm font-medium">
              <button type="button" className="btn btn-dark btn-icon .text-nowrap ms-3">
                <span className="btn-inner--icon me-1">
                  <i className="fa fa-github" aria-hidden="true"></i>
                </span>
                <span className="btn-inner--text" style={{ whiteSpace: "nowrap" }}>Sign up </span>
              </button>
            </Link>
          </form>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;