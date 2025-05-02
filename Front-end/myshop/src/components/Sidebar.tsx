import React from 'react';
import { Link } from 'react-router-dom';

const Sidebar: React.FC = () => {
    return (
        <div className="bg-dark text-white vh-100 p-3" style={{ width: '250px' }}>
            <h4>Admin Panel</h4>
            <ul className="nav flex-column mt-4">
                <li className="nav-item">
                    <Link to="/dashboard/users" className="nav-link text-white">
                    Users
                    </Link>
                </li>
                <li className="nav-item">
                    <Link to="/dashboard" className="nav-link text-white">
                    Products
                    </Link>
                </li>
                <li className="nav-item">
                    <Link to="/dashboard" className="nav-link text-white">
                    Categories
                    </Link>
                </li>
            </ul>
        </div>
    );
};

export default Sidebar;
