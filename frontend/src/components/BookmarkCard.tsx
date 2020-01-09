import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import useHttp from '../hooks/http.hook';
import { host } from '../config/constant';
import { useSnackbar } from 'notistack';
import { useAuth } from '../hooks/auth.hook';

const useStyles = makeStyles({
  card: {
    margin: '10px',
    maxWidth: 400,
  },
  media: {
    height: 200,
  },
});

export interface IBookmark {
  id: number,
  link: string,
  title: string,
  description: string,
  cover: string,
  date: Date,
}

export const BookmarkCard: React.FC<IBookmark> = (props: IBookmark) => {
  const { id, link, title, description, cover, date } = props;
  const { request } = useHttp();
  const { enqueueSnackbar } = useSnackbar();
  const { token } = useAuth();

  const handleDelete = async (e: any) => {
    try {
      const data = await request(
        host + '/api/bookmark/' + id,
        'DELETE', undefined,
        new Headers({
          'Authorization': `Bearer ${token}`
        }))
      enqueueSnackbar(data.message, { variant: 'success' });
    } catch (e) {
      enqueueSnackbar(e.message, { variant: 'error' });
    }
  }

  const classes = useStyles();
  return (
    <Card className={classes.card}>
      <CardActionArea>
        {(cover === null) ? cover : <CardMedia
          className={classes.media}
          image={cover}
          title="Contemplative Reptile"
        />}
        <CardContent>
          <Typography gutterBottom variant="h5" component="h2">
            {(title === null) ? link : title}
          </Typography>
          <Typography variant="body2" color="textSecondary" component="p">
            {description}
          </Typography>
          <Typography variant="body1" color="textSecondary" component="p">
            Created at: {new Date(date).toLocaleString()}
          </Typography>
        </CardContent>
      </CardActionArea>
      <CardActions>
        <Button size="small" color="primary" href={link}>
          Go to the site
        </Button>
        <Button size="small" color="secondary" onClick={handleDelete}>
          Delete
        </Button>
      </CardActions>
    </Card>
  )
}

export default BookmarkCard;