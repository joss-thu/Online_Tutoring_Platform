// src/context/AuthContext.js
import { createContext, useContext, useState, useEffect } from "react";
import { getUserFromToken, saveToken, removeToken } from "./AuthService";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const userData = getUserFromToken();
    setUser(userData);
  }, []);

  const login = (token) => {
    saveToken(token);
    setUser(getUserFromToken());
  };

  const logout = () => {
    removeToken();
    setUser(null);
  };

  return (
    <AuthContext.Provider
      value={{ user, login, logout, isAuthenticated: !!user }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
