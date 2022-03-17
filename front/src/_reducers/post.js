import {
  CHANGE_ACTIVEID,
  CHANGE_VIEWMODE,
  SAVE_POSTCONTENTS,
  LOAD_POST_CONTENTS,
  LOAD_TEMPPOST_CONTENTS,
  LOAD_POST_TOC,
} from '../_actions/types';
import shortid from 'shortid';
import { 
  dummyDataForContents,
  dummyDataForTOC 
} from './dummydata';

const initialState = {
  activeId : '',
  showPreview: false,
  modifyingPostContents: {
    postId: shortid.generate(),
    title: '',
    contents: []
  },
  PostData: {
    postId: shortid.generate(),
    title: '',
    contents: []
  },
  TOCData : [],
}

export default function postReducer(state = initialState, action){
    switch (action.type){
      case CHANGE_ACTIVEID: 
        return {
          ...state,
          activeId: action.data
        }
      case CHANGE_VIEWMODE: 
        return {
          ...state,
          showPreview: action.data
        }
      case SAVE_POSTCONTENTS: 
        return {
          ...state,
          modifyingPostContents: action.data
        }
      case LOAD_POST_CONTENTS: 
        // Axios
        return {
          ...state,
          PostData: dummyDataForContents
        }
      case LOAD_TEMPPOST_CONTENTS: {
        // Axios
        const tempPostContents = state.modifyingPostContents;
        return {
          ...state,
          PostData: tempPostContents
        }
      }
      case LOAD_POST_TOC: 
        // Axios
        return {
          ...state,
          TOCData: dummyDataForTOC
        }
      default: return state;
    }
}