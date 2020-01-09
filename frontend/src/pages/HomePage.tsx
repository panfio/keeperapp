import React from 'react';
import Grid from '@material-ui/core/Grid';
import CardLink, { ICard } from '../components/CardLink';

export const HomePage = () => {
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
  return (
      <Grid container direction="row-reverse" justify="center" alignItems="center">
        {cards.map((card: ICard, idx: number) => <CardLink key={idx} {...card} />)}
      </Grid>
  )
}

export default HomePage;