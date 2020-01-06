import React from 'react';
import LinkInfo, { ILinkInfo } from '../components/LinkInfo';
import List from '@material-ui/core/List';

interface ILinksList {
  links: ILinkInfo[];
}

export const LinksList: React.FC<ILinksList> = (props: ILinksList) => {

  return (
    <List >
      {
        props.links.map(
          (link: ILinkInfo) => <LinkInfo key={'' + link.id} {...link} />)
      }
    </List>
  )
}

export default LinksList;