import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { loginUser } from '../api/UserAPI'; 
import { UserLogin } from '../types/User';

const Login: React.FC = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    email: '',
    password: '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const data: UserLogin = {
      username: form.email,
      password: form.password,
    };

    loginUser(data)
      .then((jwt) => {
        console.log('Login successful:', jwt);
        toast.success('Logged in successfully!');
        navigate('/', { state: { showToast: true } });
      })
      .catch((error) => {
        console.error('Login error:', error);
        toast.error('Login failed: ' + (error.response?.data || error.message));
      });
  };

  return (
    <div className="container py-5 d-flex justify-content-center align-items-center flex-grow-1">
      <div className="card w-100" style={{ maxWidth: '400px' }}>
        <div className="card-body p-4">
          <h2 className="card-title text-center mb-4">Login</h2>

          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="email" className="form-label">Email</label>
              <input
                type="email"
                name="email"
                id="email"
                className="form-control"
                value={form.email}
                onChange={handleChange}
                required
              />
            </div>

            <div className="mb-3">
              <label htmlFor="password" className="form-label">Password</label>
              <input
                type="password"
                name="password"
                id="password"
                className="form-control"
                value={form.password}
                onChange={handleChange}
                required
              />
            </div>

            <button
              type="submit"
              className="btn btn-primary w-100"
            >
              Login
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Login;
