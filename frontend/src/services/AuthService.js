// src/services/authService.js
import { jwtDecode } from "jwt-decode";

const TOKEN_KEY = "jwt";

export const saveToken = (token) => localStorage.setItem(TOKEN_KEY, token);

export const getRoles = () => {
  const user = getUserFromToken();
  return user?.roles;
};

export const getToken = () => localStorage.getItem(TOKEN_KEY);

export const removeToken = () => localStorage.removeItem(TOKEN_KEY);

export const getUserFromToken = () => {
  const token = getToken();
  if (!token) return null;
  console.log(token);
  try {
    return jwtDecode(token);
  } catch (error) {
    return null;
  }
};

export const isAuthenticated = () => !!getUserFromToken();

export const hasRole = (role) => {
  const user = getUserFromToken();
  return user?.roles?.includes(role);
};
