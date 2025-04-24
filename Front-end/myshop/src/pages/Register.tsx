import React, { useState } from 'react';
import { CreateUser } from '../types/User';
import { registerUser } from '../api/UserAPI';
import { toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from "react-router-dom";


const Register: React.FC = () => {
    const navigate = useNavigate();
    const [form, setForm] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        confirmPassword: '',
    });

    const [error, setError] = useState('');

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        const updatedForm = { ...form, [name]: value };
        setForm(updatedForm);
                
        // Real-time password match check
        if (name === 'password' || name === 'confirmPassword') {
            if (updatedForm.password && updatedForm.confirmPassword && updatedForm.password !== updatedForm.confirmPassword) {
                setError('Passwords do not match');                
            } else {
                setError('');
            }
        }
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        setError('');

        const userData: CreateUser = {
            firstName: form.firstName,
            lastName: form.lastName,
            email: form.email,
            password: form.password,
        };

        registerUser(userData)
            .then((user) => {
                console.log("User registered successfully:", user);
                toast.success("User registered successfully!");
                navigate("/", { state: { showToast: true } });
            })
            .catch((error) => {
                console.error("Error during registration:", error);
                toast.error("Error during registration:" + error);
            });
    };

    return (
        <div className="flex justify-center py-16 px-4 bg-white flex-grow">
            <div className="w-full max-w-2xl shadow-2xl rounded-xl overflow-hidden bg-white">
                <div className="px-10 py-12">
                    <h2 className="text-3xl font-semibold text-center mb-10">Register</h2>

                    <form onSubmit={handleSubmit} className="space-y-6">
                        <div>
                            <label className="block mb-1 font-medium text-gray-700">First Name</label>
                            <input
                                type="text"
                                name="firstName"
                                value={form.firstName}
                                onChange={handleChange}
                                required
                                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                            />
                        </div>
                        <div>
                            <label className="block mb-1 font-medium text-gray-700">Last Name</label>
                            <input
                                type="text"
                                name="lastName"
                                value={form.lastName}
                                onChange={handleChange}
                                required
                                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                            />
                        </div>

                        <div>
                            <label className="block mb-1 font-medium text-gray-700">Email</label>
                            <input
                                type="email"
                                name="email"
                                value={form.email}
                                onChange={handleChange}
                                required
                                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                            />
                        </div>

                        <div>
                            <label className="block mb-1 font-medium text-gray-700">Password</label>
                            <input
                                type="password"
                                name="password"
                                value={form.password}
                                onChange={handleChange}
                                required
                                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                            />
                        </div>

                        <div>
                            <label className="block mb-1 font-medium text-gray-700">Confirm Password</label>
                            <input
                                type="password"
                                name="confirmPassword"
                                value={form.confirmPassword}
                                onChange={handleChange}
                                required
                                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
                            />
                            {/* Show real-time error here */}
                            {error && (
                                <p className="text-red-500 text-sm mt-1">{error}</p>
                            )}
                        </div>

                        <button
                            type="submit"
                            className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 rounded-lg transition"
                        >
                            Register
                        </button>
                    </form>
                </div>
            </div>
            
        </div>
    );
};

export default Register;
