import React from 'react';
import ListItem from '@material-ui/core/ListItem';
import Divider from '@material-ui/core/Divider';
import ListItemText from '@material-ui/core/ListItemText';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';

export interface ILinkInfo {
  id: number,
  name: string,
  link: string,
  cut: string,
  date: Date,
}

export const LinkInfo: React.FC<ILinkInfo> = (props: ILinkInfo) => {
  const { name, link, cut, date } = props;
  ////          ////
  //// Hardcode ////
  ////          ////
  const cutLink = "http://localhost:8088/to/" + cut;

  return (
    <React.Fragment>
      <ListItem alignItems="flex-start">
        <ListItemAvatar>
          <a href={link} >
            <i className="material-icons">link</i>
          </a>
        </ListItemAvatar>
        <ListItemText
          primary={<a href={link}>{name}</a>}
          secondary={
            <React.Fragment>
              <b>Raw: </b> {link}<br />
              <b>Cut: </b><a href={cutLink} >{cutLink}</a>  <br />
              <b>Date: </b>{new Date(date).toLocaleString()}<br />
            </React.Fragment>
          }
        />
      </ListItem>
      <Divider variant="inset" component="li" />
    </React.Fragment>
  )
}

export default LinkInfo;