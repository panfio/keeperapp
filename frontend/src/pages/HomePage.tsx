import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';

const useStyles = makeStyles({
  card: {
    margin: '10px',
    maxWidth: 400,
  },
  media: {
    height: 200,
  },
});

export interface ICard {
  name: string,
  link: string,
  text: string,
  image: string,
}

const cards: ICard[] = [
  {
    name: "Telescreen",
    image: "https://raw.githubusercontent.com/panfio/demohttpd/master/website/telescreen.jpg",
    link: "https://github.com/panfio/telescreen",
    text: `Telescreen helps aggregate and monitor your
  actions in one place and visually present them on a timeline. 
  Now you will know how and where your time was spent.`,
  },
  {
    name: "Keeper App",
    image: "https://raw.githubusercontent.com/panfio/demohttpd/master/website/keeper.jpg",
    link: "https://github.com/panfio/keeperapp",
    text: `With the Keeper App you can crop links, 
    save bookmarks, write and share articles`,
  }
]

export const CardWrapper: React.FC<ICard> = (props: ICard) => {
  const { name, link, image, text } = props;
  const classes = useStyles();
  return (
    <Card className={classes.card}>
      <CardActionArea>
        <CardMedia
          className={classes.media}
          image={image}
          title="Contemplative Reptile"
        />
        <CardContent>
          <Typography gutterBottom variant="h5" component="h2">
            {name}
          </Typography>
          <Typography variant="body2" color="textSecondary" component="p">
            {text}
          </Typography>
        </CardContent>
      </CardActionArea>
      <CardActions>
        <Button size="small" color="primary" href={link}>
          Learn More
          </Button>
      </CardActions>
    </Card>
  )
}

export const HomePage = () => {
    return (
    <div>
      <Grid
        container direction="row-reverse"
        justify="center" alignItems="center"
      >
        {
        cards.map(
          (card: ICard, idx: number) => <CardWrapper key={idx} {...card} />)
      }
      </Grid>
    </div>
  )
}

export default HomePage;