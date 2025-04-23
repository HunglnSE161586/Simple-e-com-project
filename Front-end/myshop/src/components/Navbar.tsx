const Navbar = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
      <div className="container">

        <a className="navbar-brand fw-bold" href="#">
          <img src="https://media.printables.com/media/prints/415756/images/3449559_b2fc84f0-f031-48f9-9cd9-3bc455ed5200/thumbs/inside/1280x960/png/qian-nian-xue-yuan-millennium.webp"
            className="navbar-logo"
            alt="logo"
          />
        </a>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon" />
        </button>

        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item">
              <a className="nav-link active" aria-current="page" href="#">
                Home
              </a>
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
          <form className="d-flex" role="search">
            <input
              className="form-control me-2"
              type="search"
              placeholder="Product name"
              aria-label="Search"
            />
            <button className="btn btn-outline-primary" type="submit">
              Search
            </button>
            <button type="button" className="btn btn-dark btn-icon .text-nowrap ms-3">
              <span className="btn-inner--icon me-1">
                <i className="fa fa-github" aria-hidden="true"></i>
              </span>
              <span className="btn-inner--text" style={{ whiteSpace: "nowrap" }}>Sign in </span>
            </button>
            <button type="button" className="btn btn-dark btn-icon .text-nowrap ms-3">
              <span className="btn-inner--icon me-1">
                <i className="fa fa-github" aria-hidden="true"></i>
              </span>
              <span className="btn-inner--text" style={{ whiteSpace: "nowrap" }}>Sign up </span>
            </button>
          </form>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;