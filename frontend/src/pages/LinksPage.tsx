import React, { useState, useEffect, useCallback } from 'react';
import useHttp from '../hooks/http.hook';
import { useAuth } from '../hooks/auth.hook';
import { host } from '../config/constant';

import LoadingBar from '../components/LoadingBar';
import { useSnackbar } from 'notistack';
import { ILinkInfo } from '../components/LinkInfo';
import LinksList from '../components/LinksList'
import Button from '@material-ui/core/Button';
import Container from '@material-ui/core/Container';
import Typography from '@material-ui/core/Typography';
import CreateForm from '../components/CreateForm';

export const LinksPage: React.FC = () => {

  const initialState: ILinkInfo[] = [];
  const { token, userId } = useAuth();
  const { loading, request } = useHttp();
  const { enqueueSnackbar } = useSnackbar();
  const [isOpen, setOpenForm] = useState(false);
  const [link, setLink] = useState('');
  const [links, setLinks] = useState(initialState);

  const fetchLinks = useCallback(async () => {
    try {
      const data = await request(
        host + '/api/link?userId=' + userId,
        'GET', 
        undefined,
        new Headers({
          'Authorization': `Bearer ${token}`
        }))
      setLinks(data);
    } catch (e) {
      console.log(e)
      enqueueSnackbar(e.message, { variant: 'error' });
    }
  }, [token, request, enqueueSnackbar, userId]);

  useEffect(() => {
    fetchLinks();
  }, [fetchLinks])

  const handleOpenForm = () => {
    setOpenForm(true);
  };

  const handleCloseForm = () => {
    setOpenForm(false);
  };

  const handleFormInputChange = (e: any) => {
    setLink(e.target.value)
  }

  const handleCreate = async () => {
    try {
      if (!link) {
        enqueueSnackbar('Emtpy link', { variant: 'error' });
        return;
      }
      const data = await request(
        host + '/api/link/create',
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

  const createLinkFormProps = {
    isOpen: isOpen,
    title: 'Create link',
    text: 'Paste the link here, and our server will do all the dirty work for you',
    value: link,
    handleInputChange: handleFormInputChange,
    handleCloseForm: handleCloseForm,
    handleCreate: handleCreate,
  }
  
  return (
    <Container fixed >
      {(loading) ? <LoadingBar /> : undefined}
      <Typography variant="h3">
        My links
        <Button
          variant="outlined" color="primary" style={{ marginLeft: '10px' }}
          onClick={handleOpenForm}>
          Add
        </Button>
      </Typography>
      <LinksList links={links} />
      <CreateForm {...createLinkFormProps} />
    </Container>
  )
}

export default LinksPage;