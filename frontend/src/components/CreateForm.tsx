import React from 'react';

import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

export interface ICreateForm {
  isOpen: boolean,
  title: string,
  text: string,
  value: string,
  handleInputChange: any,
  handleCloseForm: any,
  handleCreate: any,
}

export const CreateForm: React.FC<ICreateForm> = (props: ICreateForm) => {
  const { isOpen, title, text, value,
    handleInputChange, handleCloseForm, handleCreate } = props;
  return (
    <Dialog open={isOpen} aria-labelledby="form-dialog-title">
      <DialogTitle id="form-dialog-title">{title}</DialogTitle>
      <DialogContent>
        <DialogContentText>{text}</DialogContentText>
        <TextField
          autoFocus margin="dense" id="link"
          value={value} onChange={handleInputChange}
          label="Link" type="email" fullWidth
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
  )
}

export default CreateForm;