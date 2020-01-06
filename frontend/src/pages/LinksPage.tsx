import React, { useState, useEffect, useCallback } from 'react';
import useHttp from '../hooks/http.hook';
import { useAuth } from '../hooks/auth.hook';

import LinksList from '../components/LinksList'
import Button from '@material-ui/core/Button';
import Container from '@material-ui/core/Container';
import Typography from '@material-ui/core/Typography';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import LoadingBar from '../components/LoadingBar';
import { useSnackbar } from 'notistack';
import { ILinkInfo } from '../components/LinkInfo';

export const LinksPage: React.FC = () => {

  const initialState: ILinkInfo[] = [];
  const { token, userId } = useAuth();
  const { loading, request } = useHttp();
  const { enqueueSnackbar } = useSnackbar();
  const [open, setOpenForm] = useState(false);
  const [link, setLink] = useState('');
  const [links, setLinks] = useState(initialState);

  const fetchLinks = useCallback(async () => {
    try {
      const data = await request(
        //HARDCODE
        'http://localhost:8088/api/links',
        'GET', undefined,
        new Headers({
          'Authorization': `Bearer ${token}`
        }))
      setLinks(data);
    } catch (e) {
      console.log(e)
      enqueueSnackbar(e.message, { variant: 'error' });
    }
  }, [token, request, enqueueSnackbar]);

  useEffect(() => {
    fetchLinks();
  }, [fetchLinks])

  const handleOpenForm = () => {
    setOpenForm(true);
  };

  const handleCloseForm = () => {
    setOpenForm(false);
  };

  const handleCreate = async () => {
    try {
      if (!link) {
        enqueueSnackbar('Emtpy link', { variant: 'error' });
        return;
      }
      const data = await request(
        //HARDCODE
        'http://localhost:8088/api/links/create',
        'POST', JSON.stringify({ link, userId }),
        new Headers({
          'Content-type': 'application/json',
          'Authorization': `Bearer ${token}`
        }))
      setLink('');
      enqueueSnackbar(data.message, { variant: 'success' });
      fetchLinks()
    } catch (e) {
      enqueueSnackbar(e.message, { variant: 'error' });
    }
    setOpenForm(false);
  };

  return (
    <Container fixed >
      {(loading) ? <LoadingBar /> : undefined}
      <Typography variant="h3">
        My links
        <Button
          variant="outlined"
          color="primary"
          style={{ marginLeft: '10px' }}
          onClick={handleOpenForm}>
          Add
        </Button>
      </Typography>

      <LinksList links={links} />
      <Dialog open={open} aria-labelledby="form-dialog-title">
        <DialogTitle id="form-dialog-title">Create link</DialogTitle>
        <DialogContent>
          <DialogContentText>
            Paste the link here, and our server will do all the dirty work for you
          </DialogContentText>
          <TextField
            autoFocus
            margin="dense"
            id="link"
            value={link}
            onChange={e => setLink(e.target.value)}
            label="Link"
            type="email"
            fullWidth
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseForm} color="primary">
            Cancel
          </Button>
          <Button onClick={handleCreate} color="primary">
            Create
          </Button>
        </DialogActions>
      </Dialog>
    </Container>
  )
}

export default LinksPage;