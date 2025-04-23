

const HeroSection = () => {
  return (
    <section className="bg-light text-dark py-5">
      <div className="container">
        <div className="row align-items-center">
          {/* Text content */}
          <div className="col-md-6 mb-4 mb-md-0">
            <h1 className="display-4 fw-bold">Welcome to Millennium Mart</h1>
            <p className="lead">
              Shop from the latest collections of high-tech equipments, tools, and many more—all in one place.
            </p>
            <a href="/shop" className="btn btn-warning btn-lg mt-3">
              Shop Now
            </a>
          </div>

          {/* Image */}
          <div className="col-md-6 text-center">
            <img
              src="https://plus.unsplash.com/premium_photo-1676998931123-75789162f170?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8aGVybyUyMGltYWdlfGVufDB8fDB8fHww"
              alt="Hero Banner"
              className="img-fluid rounded shadow"
            />
          </div>
        </div>
      </div>
    </section>
  );
};

export default HeroSection;
