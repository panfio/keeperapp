import { createContext } from 'react';

function login(jwtToken: string, id: number){}
function logout(){}

export const AuthContext = createContext({
  token: '',
  userId: -1,
  login: login,
  logout: logout,
  isAuthenticated: false
})

export default AuthContext;