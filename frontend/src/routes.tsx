import React from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';
import { LinksPage } from './pages/LinksPage';
import LoginPage from './pages/LoginPage';
import HomePage from './pages/HomePage';
import SignUpPage from './pages/SignUpPage';
import PostPage from './pages/PostPage';
import BookmarkPage from './pages/BookmarkPage';

export const useRoutes = (isAuthenticated: Boolean) => {
  if (isAuthenticated) {
    return (
      <Switch>
        <Route path="/" exact>
          <HomePage />
        </Route>
        <Route path="/links" exact>
          <LinksPage />
        </Route>
        <Route path="/posts" exact>
          <PostPage />
        </Route>
        <Route path="/bookmarks" exact>
          <BookmarkPage />
        </Route>
        <Route path="/login" exact>
          <LoginPage />
        </Route>
        <Redirect to="/" />
      </Switch>
    )
  }
  return (
    <Switch>
      <Route path="/" exact>
          <HomePage />
      </Route>
      <Route path="/login" exact>
        <LoginPage />
      </Route>
      <Route path="/signup" exact>
        <SignUpPage />
      </Route>
      <Redirect to="/login" />
    </Switch>
  )
}