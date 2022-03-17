import {
  CHANGE_ACTIVEID,
  SAVE_POSTCONTENTS
} from './types';

export const changeActiveIdAction = (data) => {
  return {
    type: CHANGE_ACTIVEID,
    data,
  }
}

export const savePostContentsAction = (data, temporaryMode = false) => {
  return {
    type: SAVE_POSTCONTENTS,
    data,
    temporaryMode,
  }
}
