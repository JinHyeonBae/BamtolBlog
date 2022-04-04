import { createSlice } from '@reduxjs/toolkit';
import { 
  dummyDataForContents,
  dummyDataForTOC 
} from './dummydata';
import shortid from 'shortid';

const initialState = {
  value: 0,
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

export const postSlice = createSlice({
  name: 'post',
  initialState,
  reducers: {
    addValue:(state) => {
      state.value += 1;
    },
    changeViewModeToPreview:(state, action) => {
      state.showPreview = action.payload;
    },
    savePostContents:(state, action) => {
      state.modifyingPostContents = action.payload;
    },
    setActiveId: (state, action) => {
      state.activeId = action.payload;
    },
    loadPostContents: (state) => {
      state.PostData = dummyDataForContents; 
    },
    loadTempPostContents: (state) => {
      const tempPostContents = state.modifyingPostContents;
      state.PostData = tempPostContents;
    },
    loadPostTOC: (state) => {
      state.TOCData = dummyDataForTOC;
    }
  },
})

export const { 
  addValue, 
  changeViewModeToPreview,
  savePostContents,
  setActiveId, 
  loadPostContents, 
  loadTempPostContents, 
  loadPostTOC 
} = postSlice.actions;

export default postSlice.reducer;

export const selectValue = (state) => state.post.value;
export const selectActiveId = (state) => state.post.activeId;
export const selectPostData = (state) => state.post.PostData;
export const selectPostTOC = (state) => state.post.TOCData;
export const selectShowPreview = (state) => state.post.showPreview;
export const selectModifyingPostData = (state) => state.post.modifyingPostContents;