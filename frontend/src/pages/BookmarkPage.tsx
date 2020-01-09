import React, { useState, useEffect, useCallback } from 'react';
import useHttp from '../hooks/http.hook';
import { useAuth } from '../hooks/auth.hook';
import { useSnackbar } from 'notistack';
import { host } from '../config/constant';

import Grid from '@material-ui/core/Grid';
import LoadingBar from '../components/LoadingBar';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import CreateForm from '../components/CreateForm';
import BookmarkCard, { IBookmark } from '../components/BookmarkCard';

export const BookmarkPage: React.FC = () => {
  
  const initialState: IBookmark[] = [];
  const [bookmarks, setBookmarks] = useState(initialState);
  const [isOpen, setOpenForm] = useState(false);
  const [link, setLink] = useState('');

  const { token, userId } = useAuth();
  const { loading, request } = useHttp();
  const { enqueueSnackbar } = useSnackbar();

  const handleOpenForm = () => {
    setOpenForm(true);
  };

  const handleCloseForm = () => {
    setOpenForm(false);
  };

  const handleFormInputChange = (e: any) => {
    setLink(e.target.value)
  }

  const fetchBookmarks = useCallback(async () => {
    try {
      const data = await request(
        host + '/api/bookmark?userId=' + userId,
        'GET', 
        undefined,
        new Headers({
          'Authorization': `Bearer ${token}`
        }))
      setBookmarks(data);
    } catch (e) {
      console.log(e)
      enqueueSnackbar(e.message, { variant: 'error' });
    }
  }, [token, request, enqueueSnackbar, userId]);


  useEffect(() => {
    fetchBookmarks();
  }, [fetchBookmarks])

  const handleCreate = async () => {
    try {
      if (!link) {
        enqueueSnackbar('Emtpy link', { variant: 'error' });
        return;
      }
      const data = await request(
        host + '/api/bookmark/create',
        'POST', JSON.stringify({ link, userId }),
        new Headers({
          'Content-type': 'application/json',
          'Authorization': `Bearer ${token}`
        }))
      setLink('');
      enqueueSnackbar(data.message, { variant: 'success' });
      fetchBookmarks()
    } catch (e) {
      enqueueSnackbar(e.message, { variant: 'error' });
    }
    setOpenForm(false);
  };

  const createLinkFormProps = {
    isOpen: isOpen,
    title: 'Create bookmark',
    text: 'Paste the link here, and our server will do all the dirty work for you',
    value: link,
    handleInputChange: handleFormInputChange,
    handleCloseForm: handleCloseForm,
    handleCreate: handleCreate,
  }

  return (
    <React.Fragment >
      {(loading) ? <LoadingBar /> : undefined}
      <Typography variant="h3">
        My bookmarks
        <Button
          variant="outlined" color="primary" style={{ marginLeft: '10px' }}
          onClick={handleOpenForm}>
          Add
        </Button>
      </Typography>
      <CreateForm {...createLinkFormProps} />

      {/* create HOC */}
      <Grid container direction="row-reverse" justify="center" alignItems="center">
        {bookmarks.map((bookmark: IBookmark, idx: number) => <BookmarkCard key={idx} {...bookmark} />)}
      </Grid>
    </React.Fragment>
  )
}

export default BookmarkPage;