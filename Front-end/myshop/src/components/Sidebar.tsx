import React from 'react';

const Sidebar: React.FC = () => {
    return (
        <div className="bg-dark text-white vh-100 p-3" style={{ width: '250px' }}>
            <h4>Admin Panel</h4>
            <ul className="nav flex-column mt-4">
                <li className="nav-item">
                    <a className="nav-link text-white" href="#">Users</a>
                </li>
                <li className="nav-item">
                    <a className="nav-link text-white" href="#">Products</a>
                </li>
                <li className="nav-item">
                    <a className="nav-link text-white" href="#">Categories</a>
                </li>
                <li className="nav-item">
                    <a className="nav-link text-white" href="#">Orders</a>
                </li>
            </ul>
        </div>
    );
};

export default Sidebar;
