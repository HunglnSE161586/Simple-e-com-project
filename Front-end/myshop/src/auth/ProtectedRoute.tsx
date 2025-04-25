// src/routes/ProtectedRoute.tsx
import  {Navigate}  from 'react-router-dom';
import { getToken } from './Token';
import { getUserRole } from './JwtDecode';
import React from 'react';

type Props = {
  role: string;
  children: React.ReactNode;
};

const ProtectedRoute = ({ role, children }: Props) => {
  const token = getToken();
  const userRole = token ? getUserRole(token) : null;

  if (!token) return <Navigate to="/login" />;
  if (userRole !== role) return <Navigate to="/unauthorized" />;

  return children;
};

export default ProtectedRoute;
