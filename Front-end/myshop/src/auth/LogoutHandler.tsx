import { useNavigate } from 'react-router-dom';
import { removeToken } from './Token';
import { toast } from 'react-toastify';
import { logoutUser } from '../api/UserAPI';  // Import your API logout function here

export const useHandleLogout = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    logoutUser()
      .then((response) => {
        removeToken();
        toast.success(response);
        navigate('/');
      })
      .catch((error) => {
        console.error('Logout error:', error);
        toast.error('Logout failed: ' + (error.response?.data || error.message));
      });
  };

  return handleLogout;
};
