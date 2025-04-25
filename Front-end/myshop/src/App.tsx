import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ComplexNavbar from "./components/Navbar";
import Footer from "./components/Footer";
import Register from "./pages/Register"; 
import Home from "./pages/Home"
import { ToastContainer } from "react-toastify";

import './App.css';

import './App.css'
import Login from "./pages/Login";

function App() {
  return (
    <Router>
      <div className="App">
        <ComplexNavbar />

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
        </Routes>

        <Footer />
        <ToastContainer position="top-right" autoClose={3000} />
      </div>
    </Router>
  );
}

export default App
