import {jwtDecode} from 'jwt-decode';

interface RoleObject {
  authority: string;
}

interface JwtPayload {
  sub: string;
  userId:number;
  role: RoleObject[];
  iat: number;
  exp: number;
}

export const getUserRole = (token: string): string | null => {
  try {
    const decoded = jwtDecode<JwtPayload>(token);
    const roles = decoded.role;

    if (Array.isArray(roles) && roles.length > 0) {
      return roles[0].authority; // or handle multiple roles
    }

    return null;
  } catch (e) {
    console.error("Invalid token", e);
    return null;
  }
};
export const getUserId=(token:string): number | null => {
  try {
    const decoded = jwtDecode<JwtPayload>(token);
    const userId = decoded.userId;

    return userId;
  } catch (e) {
    console.error("Invalid token", e);
    return null;
  }
};
