import React, { useEffect, useState } from 'react';
import Topbar from '../components/Topbar';
import Sidebar from '../components/Sidebar';
import { User } from '../types/User';
import { getUsersPaged, softDeleteUser, softRestoreUser } from '../api/UserAPI';
import { PaginatedResponse } from '../types/PaginatedResponse';
import { toast } from 'react-toastify';

const UserManagement: React.FC = () => {
    const [users, setUsers] = useState<User[]>([]);
    const [page, setPage] = useState(0);
    const [size] = useState(10);
    const [totalPages, setTotalPages] = useState(1);

    const fetchUsers = async () => {
        try {
            const response: PaginatedResponse<User> = await getUsersPaged(page, size);
            setUsers(response.content);
            setTotalPages(response.totalPages);
        } catch (error) {
            console.error('Failed to fetch users:', error);
        }
    };

    useEffect(() => {
        fetchUsers();
    }, [page]);

    const handlePrevious = () => {
        if (page > 0) setPage(prev => prev - 1);
    };

    const handleNext = () => {
        if (page < totalPages - 1) setPage(prev => prev + 1);
    };
    const handleDelete = async (userId: number) => {
        try {
            await softDeleteUser(userId);
            fetchUsers(); // Refresh the user list after deletion
            toast.success("Delete success!");
        } catch (error) {
            console.error('Failed to delete user:', error);
        }
    };

    const handleRestore = async (userId: number) => {
        try {
            await softRestoreUser(userId);
            fetchUsers(); // Refresh the user list after restoration
            toast.success("Restore success!");
        } catch (error) {
            console.error('Failed to restore user:', error);
        }
    };


    return (
        <div className="d-flex">
            <Sidebar />
            <div className="flex-grow-1">
                <Topbar />
                <div className="p-4">
                    <h2 className="text-center mb-4">User Management</h2>
                    <div className="table-responsive">
                        <table className="table table-bordered table-hover">
                            <thead className="table-light">
                                <tr>
                                    <th>ID</th>
                                    <th>Email</th>
                                    <th>Name</th>
                                    <th>Status</th>
                                    <th>Created</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {users.map(user => (
                                    <tr key={user.userId}>
                                        <td>{user.userId}</td>
                                        <td>{user.email}</td>
                                        <td>{user.firstName} {user.lastName}</td>
                                        <td>
                                            {user.isActive ? (
                                                <span className="badge bg-success">Active</span>
                                            ) : (
                                                <span className="badge bg-secondary">Inactive</span>
                                            )}
                                        </td>
                                        <td>{new Date(user.createdAt).toLocaleString()}</td>
                                        <td>
                                            <button className="btn btn-sm btn-primary me-2">Edit</button>
                                            {user.isActive ? (
                                                <button
                                                    className="btn btn-sm btn-danger"
                                                    onClick={() => handleDelete(user.userId)}
                                                >
                                                    Delete
                                                </button>
                                            ) : (
                                                <button
                                                    className="btn btn-sm btn-success"
                                                    onClick={() => handleRestore(user.userId)}
                                                >
                                                    Restore
                                                </button>
                                            )}
                                        </td>
                                    </tr>
                                ))}
                                {users.length === 0 && (
                                    <tr>
                                        <td colSpan={6} className="text-center">No users found.</td>
                                    </tr>
                                )}
                            </tbody>
                        </table>
                    </div>

                    <div className="d-flex justify-content-between align-items-center mt-3">
                        <button
                            className="btn btn-outline-primary"
                            onClick={handlePrevious}
                            disabled={page === 0}
                        >
                            Previous
                        </button>
                        <span>Page {page + 1} of {totalPages}</span>
                        <button
                            className="btn btn-outline-primary"
                            onClick={handleNext}
                            disabled={page >= totalPages - 1}
                        >
                            Next
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default UserManagement;
