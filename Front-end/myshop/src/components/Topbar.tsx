import React from 'react';
import { useHandleLogout } from '../auth/LogoutHandler';



const Topbar: React.FC = () => {
    const handleLogout=useHandleLogout();
    return (
        <nav className="navbar navbar-expand navbar-light bg-light px-4 shadow-sm">
            <span className="navbar-brand mb-0 h1">Dashboard</span>
            <span className="me-3">
                <button onClick={handleLogout} className="btn btn-dark btn-icon ">
                    Logout
                </button>
            </span>
            <div className="ms-auto d-flex align-items-center">
                <span className="me-3">Welcome, Admin</span>
                <img
                    src="https://via.placeholder.com/30"
                    className="rounded-circle"
                    alt="Profile"
                />                
            </div>
        </nav>
    );
};

export default Topbar;
