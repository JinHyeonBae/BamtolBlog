import {
  CHANGE_ACTIVEID_ACTION
} from '../_actions/types';

const initialState = {
  hash: '',
  activeId : '',
}

export default function postReducer(state = initialState, action){
    switch (action.type){
      case CHANGE_ACTIVEID_ACTION: 
        return {
          ...state,
          activeId: action.data
        }
      default: return state;
    }
}