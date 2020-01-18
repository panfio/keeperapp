import { useCallback, useState, useEffect } from 'react';

const userDataStorage = 'userData';
export const useAuth = () => {

  // fragile
  const initData = JSON.parse(localStorage.getItem(userDataStorage)!);
  const initToken: string = (initData && initData.token) ? initData.token : '';
  const initUserId: number = (initData && initData.userId) ? initData.userId : -1;

  const [token, setToken] = useState(initToken);
  const [userId, setUserId] = useState(initUserId);

  const login = useCallback((jwtToken: string, id: number) => {
    setToken(jwtToken);
    setUserId(id);

    localStorage.setItem(userDataStorage, JSON.stringify({
      userId: id, token: jwtToken
    }))
  }, [])

  const logout = useCallback(() => {
    setToken('');
    setUserId(-1);
    localStorage.removeItem(userDataStorage);
  }, [])

  useEffect(() => {
    const data = JSON.parse(localStorage.getItem(userDataStorage)!);
    if (data && data.token) {
      login(data.token, data.userId)
    }
  }, [login])

  return { login, logout, token, userId }
}