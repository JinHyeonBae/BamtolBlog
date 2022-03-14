import {
  CHANGE_ACTIVEID_ACTION
} from './types';

export const changeActiveIdAction = (data) => {
  return {
    type: CHANGE_ACTIVEID_ACTION,
    data,
  }
}