import React, { useState } from 'react';
import useHttp from '../hooks/http.hook';
import { useSnackbar } from 'notistack';
import { useHistory } from 'react-router-dom';
import { host } from '../config/constant';

import TextField from '@material-ui/core/TextField';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';


const useStyles = makeStyles({
  card: {
    minWidth: 275,
  },
});

const SignUpPage: React.FC = () => {

  const history = useHistory();
  const classes = useStyles();
  const { enqueueSnackbar } = useSnackbar();
  const { request } = useHttp();

  const [form, useForm] = useState({
    username: '',
    email: '',
    name: '',
    password: ''
  })

  const signUpHandler = async () => {
    try {
      const data = await request(
        host + '/api/auth/signup',
        'POST', JSON.stringify(form),
        new Headers({
          'Content-type': 'application/json'
        }))
      enqueueSnackbar(data.message, { variant: 'success' });
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
      <Card className={classes.card}>
        <CardContent>
          <Typography variant="h4" component="h2" className={classes.card}>
            Registration Form
          </Typography>
          <div style={{ margin: '1em' }}>
            <TextField
              required fullWidth
              id="name"
              value={form.name}
              onChange={_handleTextFieldChange}
              label="Name"
              variant="outlined"
              helperText="What's yor name?" />
          </div>
          <div style={{ margin: '1em' }}>
            <TextField
              required fullWidth
              id="email"
              value={form.email}
              onChange={_handleTextFieldChange}
              label="Email"
              variant="outlined"
              helperText="Example fake@mail.ru" />
          </div>
          <div style={{ margin: '1em' }}>
            <TextField
              required fullWidth
              id="username"
              value={form.username}
              onChange={_handleTextFieldChange}
              label="Username"
              variant="outlined"
              helperText="Are you keyboardWaRrIoR42 ?" />
          </div>
          <div style={{ margin: '1em' }} >
            <TextField
              required fullWidth
              value={form.password}
              onChange={_handleTextFieldChange}
              id="password"
              label="Password"
              type="password"
              variant="outlined" 
              helperText="Password must be more than 6 character long"/>
          </div>
        </CardContent>
        <CardActions>
          <Button variant="outlined" onClick={signUpHandler} color="secondary">
            Sign Up
          </Button>
        </CardActions>
      </Card>
    </Container>
  )
}

export default SignUpPage;