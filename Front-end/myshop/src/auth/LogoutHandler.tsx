import { useNavigate } from 'react-router-dom';
import { removeToken } from './Token';
import { toast } from 'react-toastify';
import { logoutUser } from '../api/UserAPI';

export const useHandleLogout = () => {
  const navigate = useNavigate();

  const handleLogout = () => {    
    logoutUser()
      .then((response) => {        
        toast.success(response);        
      })
      .catch((error) => {
        console.error('Logout error:', error);
        //toast.error('Logout failed: ' + (error.response?.data || error.message));
      }).finally(()=>{
        removeToken();
        navigate('/');
      });
  };

  return handleLogout;
};
