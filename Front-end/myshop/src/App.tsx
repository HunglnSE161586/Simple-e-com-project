import { BrowserRouter as Router, Routes, Route, useLocation } from "react-router-dom";
import ComplexNavbar from "./components/Navbar";
import Footer from "./components/Footer";
import Register from "./pages/Register";
import Home from "./pages/Home"
import { ToastContainer } from "react-toastify";

import './App.css';

import './App.css'
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import ProtectedRoute from "./auth/ProtectedRoute";
import ProductsPage from "./pages/ProductsPage"
function AppWrapper() {
  return (
    <Router>
      <App />
    </Router>
  );
}

function App() {
  const location = useLocation();
  const hideLayout = location.pathname.startsWith("/dashboard");
  return (

    <div className="App">
      {!hideLayout && <ComplexNavbar />}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
        <Route path="/products" element={<ProductsPage/>}/>
        <Route path="/dashboard" element={
          <ProtectedRoute role="ROLE_ADMIN">
            <Dashboard />
          </ProtectedRoute>} />
      </Routes>

      {!hideLayout && <Footer />}
      <ToastContainer position="top-right" autoClose={3000} />
    </div>
  );
}

export default AppWrapper
