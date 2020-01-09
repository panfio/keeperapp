import React, { useState, useContext } from 'react';
import useHttp from '../hooks/http.hook';
import { useSnackbar } from 'notistack';
import AuthContext from '../context/AuthContext';
import { Link, useHistory } from 'react-router-dom';
import { host } from '../config/constant';

import TextField from '@material-ui/core/TextField';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';

const LoginPage: React.FC = () => {

  const history = useHistory();
  const auth = useContext(AuthContext);
  const { enqueueSnackbar } = useSnackbar();
  const { request } = useHttp()
  const [form, useForm] = useState({
    usernameOrEmail: '',
    password: ''
  })

  const loginHandler = async () => {
    try {
      const data = await request(
        host + '/api/auth/signin',
        'POST', JSON.stringify(form),
        new Headers({
          'Content-type': 'application/json'
        }))
      auth.login(data.accessToken, data.userId)
      enqueueSnackbar('Login success!', { variant: 'success' });
      history.push('/');
    } catch (e) {
      enqueueSnackbar(e.message, { variant: 'error' });
    }
  }

  const _handleTextFieldChange = (e: any) => {
    useForm({
      ...form, [e.currentTarget.id]: e.target.value
    });
  }

  return (
    <Container fixed >
      <Card>
        <CardContent>
          <Typography variant="h4" component="h2">
            Login Form
          </Typography>
          <div style={{ margin: '1em' }}>
            <TextField
              required fullWidth
              value={form.usernameOrEmail}
              onChange={_handleTextFieldChange}
              id="usernameOrEmail" label="Login" variant="outlined"
              helperText="Username or Email" />
          </div>
          <div style={{ margin: '1em' }} >
            <TextField
              required fullWidth
              value={form.password}
              onChange={_handleTextFieldChange}
              id="password" label="Password" type="password"
              variant="outlined" />
          </div>
        </CardContent>
        <CardActions>
          <Button onClick={loginHandler} variant="outlined" color="primary">
            Login
          </Button>
          <Button component={Link} to={'/signup'} variant="outlined" color="secondary">
            Sign Up
          </Button>
        </CardActions>
      </Card>
    </Container>
  )
}

export default LoginPage;