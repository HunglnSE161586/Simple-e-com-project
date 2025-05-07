

const Footer = () => {
  return (
    <footer className="bg-dark text-light pt-5 pb-4">
      <div className="container text-md-left">
        <div className="row text-md-left">
          {/* Brand + Description */}
          <div className="col-md-3 col-lg-3 col-xl-3 mx-auto mt-3">
            <h5 className="text-uppercase mb-4 font-weight-bold text-warning">Millennium mart</h5>
            <p>
              Your one-stop shop for the latest collections of high-tech equipments, tools, and everyday essentials.
            </p>
          </div>

          {/* Links */}
          <div className="col-md-2 col-lg-2 col-xl-2 mx-auto mt-3">
            <h5 className="text-uppercase mb-4 font-weight-bold text-warning">Shop</h5>
            <p><a href="#" className="text-light text-decoration-none">Store</a></p>
            <p><a href="#" className="text-light text-decoration-none">Designers</a></p>
            <p><a href="#" className="text-light text-decoration-none">Fashion</a></p>
            <p><a href="#" className="text-light text-decoration-none">Pricing</a></p>
          </div>

          {/* Useful Links */}
          <div className="col-md-3 col-lg-2 col-xl-2 mx-auto mt-3">
            <h5 className="text-uppercase mb-4 font-weight-bold text-warning">Useful Links</h5>
            <p><a href="#" className="text-light text-decoration-none">Your Account</a></p>
            <p><a href="#" className="text-light text-decoration-none">Help Center</a></p>
            <p><a href="#" className="text-light text-decoration-none">Terms of Service</a></p>
            <p><a href="#" className="text-light text-decoration-none">Privacy Policy</a></p>
          </div>

          {/* Contact Info */}
          <div className="col-md-4 col-lg-3 col-xl-3 mx-auto mt-3">
            <h5 className="text-uppercase mb-4 font-weight-bold text-warning">Contact</h5>
            <p><i className="fas fa-home me-3"></i> 123 Main St, Kivotos City</p>
            <p><i className="fas fa-envelope me-3"></i> support@MillenniumMart.com</p>
            <p><i className="fas fa-phone me-3"></i> +1 234 567 890</p>
            <p><i className="fas fa-print me-3"></i> +1 234 567 891</p>
          </div>
        </div>

        {/* Social Icons */}
        <hr className="mb-4" />
        <div className="row align-items-center">
          <div className="col-md-7 col-lg-8">
            <p>© {new Date().getFullYear()} Millennium mart. All rights reserved.</p>
          </div>

          <div className="col-md-5 col-lg-4">
            <div className="text-center text-md-end">
              <a href="#" className="btn btn-outline-light btn-floating m-1"><i className="fab fa-facebook-f">Facebook</i></a>
              <a href="#" className="btn btn-outline-light btn-floating m-1"><i className="fab fa-twitter">Twitter</i></a>
              <a href="#" className="btn btn-outline-light btn-floating m-1"><i className="fab fa-instagram">Instagram</i></a>              
            </div>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
