import React from 'react';
import Topbar from '../components/Topbar';
import Sidebar from '../components/Sidebar';

const Dashboard: React.FC = () => {
    return (
        <div className="d-flex">
            <Sidebar />
            <div className="flex-grow-1">
                <Topbar />
                <div className="p-4">
                    <h2 className="text-center">Dashboard Overview</h2>
                    <div className="row mt-4">
                        <div className="col-md-4 mb-3">
                            <div className="card shadow-sm">
                                <div className="card-body">
                                    <h5 className="card-title">Users</h5>
                                    <p className="card-text">1,234 Active Users</p>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-4 mb-3">
                            <div className="card shadow-sm">
                                <div className="card-body">
                                    <h5 className="card-title">Revenue</h5>
                                    <p className="card-text">$12,340</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Dashboard;
