import React from 'react';
import { Link } from 'react-router-dom';

const Sidebar: React.FC = () => {
    return (
        <div
            className="bg-dark text-white p-3 position-sticky top-0"
            style={{ width: '250px', height: '100vh' }}
        >
            <Link to="/dashboard" className="nav-link text-white">
                <h4>Admin Panel</h4>
            </Link>
            <ul className="nav flex-column mt-4">
                <li className="nav-item">
                    <Link to="/dashboard/users" className="nav-link text-white">
                        Users
                    </Link>
                </li>
                <li className="nav-item">
                    <Link to="/dashboard/products" className="nav-link text-white">
                        Products
                    </Link>
                </li>
                <li className="nav-item">
                    <Link to="/dashboard/categories" className="nav-link text-white">
                        Categories
                    </Link>
                </li>
            </ul>
        </div>
    );
};

export default Sidebar;
