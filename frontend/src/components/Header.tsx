import React, { useContext } from 'react';
import AuthContext from '../context/AuthContext';
import { Link } from 'react-router-dom';

import { createStyles, makeStyles, Theme } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    offset: theme.mixins.toolbar,
    root: {
      flexGrow: 1,
    },
    title: {
      flexGrow: 1,
    },
  }),
);

const Header: React.FC = () => {
  const auth = useContext(AuthContext);
  const classes = useStyles();
  return (
    <React.Fragment>
      <AppBar position="fixed">
        <Toolbar>
          <Typography variant="h4" className={classes.title}>
            <Button component={Link} to={'/'} color="inherit">
              Keeper App
            </Button>
          </Typography>
          <Button component={Link} to={'/links'} color="inherit">
            Links
          </Button>
          <Button component={Link} to={'/bookmarks'} color="inherit">
            Bookmarks
          </Button>
          <Button component={Link} to={'/posts'} color="inherit">
            Posts
          </Button>
          {
            (auth.isAuthenticated)
              ? <Button color="inherit" onClick={auth.logout}>Logout</Button>
              : <Button component={Link} to={'/login'} color="inherit">Login</Button>
          }
        </Toolbar>
      </AppBar>
      <div className={classes.offset} />
    </React.Fragment>
  );
}

export default Header;