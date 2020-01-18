import React from 'react';
import './App.css';
import { useRoutes } from './routes';
import { HashRouter as Router } from 'react-router-dom';
import Header from './components/Header';
import { SnackbarProvider } from 'notistack';
import { useAuth } from './hooks/auth.hook';
import AuthContext from './context/AuthContext';

const App: React.FC = () => {
  
  const { token, login, logout, userId } = useAuth()
  let isAuthenticated = !!token;
  const routes = useRoutes(isAuthenticated);

  return (
    <AuthContext.Provider value={{
      token, login, logout, userId, isAuthenticated
    }}>
      <SnackbarProvider maxSnack={3}>
        <Router>
          <Header />
          {routes}
        </Router>
      </SnackbarProvider>
    </AuthContext.Provider >
  );
}

export default App;
