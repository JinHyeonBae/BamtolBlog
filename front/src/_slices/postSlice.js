import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axios from 'axios';
import { 
  dummyDataForTOC 
} from './dummydata';
import shortid from 'shortid';

export const loadPost = createAsyncThunk(
  "post/loadPost",
  async (loadPostData) =>{
    // const response = await axios.post(`http://localhost:8000/api/${loadPostData.userNickname}/posts/${loadPostData.postId}`, loadPostData);
    const response = await axios.post(`http://localhost:8000/api/getPostData`, loadPostData);
    return response.data.dummyDataForContents;
  }
)
export const loadPostTOC = createAsyncThunk(
  "post/loadPostTOC",
  async (loadPostTOCData) =>{
    const response = await axios.post(`http://localhost:8000/api/getPostTOCData`, loadPostTOCData);
    return response.data.dummyDataForTOC;
  }
)

const initialState = {
  loadPostStatus: 'idle',
  loadPostTOC: 'idle',
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
    author: {
      id: shortid.generate(),
      nickname: ''
    },
    contents: []
  },
  TOCData : [],
  error: null,
}

export const postSlice = createSlice({
  name: 'post',
  initialState,
  reducers: {
    changeViewModeToPreview:(state, action) => {
      state.showPreview = action.payload;
    },
    savePostContents:(state, action) => {
      state.modifyingPostContents = action.payload;
    },
    setActiveId: (state, action) => {
      state.activeId = action.payload;
    },
    loadTempPostContents: (state) => {
      const tempPostContents = state.modifyingPostContents;
      state.PostData = tempPostContents;
    },
    loadPostTOC: (state) => {
      state.TOCData = dummyDataForTOC;
    }
  },
  extraReducers: builder => {
    builder.addCase(loadPost.pending, (state)=> {
      state.loadPostStatus = 'loading';
    })
    builder.addCase(loadPost.fulfilled, (state, {payload})=> {
      state.loadPostStatus = 'success';
      state.PostData = payload;
    })
    builder.addCase(loadPost.rejected, (state, action)=> {
      state.loadPostStatus = 'failed';
      state.error = action.payload;
    })
    builder.addCase(loadPostTOC.pending, (state)=> {
      state.loadPostTOCStatus = 'loading';
    })
    builder.addCase(loadPostTOC.fulfilled, (state, {payload})=> {
      state.loadPostTOCStatus = 'success';
      state.TOCData = payload;
    })
    builder.addCase(loadPostTOC.rejected, (state, action)=> {
      state.loadPostTOCStatus = 'failed';
      state.error = action.payload;
    })
  }
})

export const { 
  addValue, 
  changeViewModeToPreview,
  savePostContents,
  setActiveId, 
  loadTempPostContents, 
} = postSlice.actions;

export default postSlice.reducer;

export const selectLoadPostStatus = (state) => state.post.loadPostStatus;
export const selectActiveId = (state) => state.post.activeId;
export const selectPostData = (state) => state.post.PostData;
export const selectPostTOC = (state) => state.post.TOCData;
export const selectShowPreview = (state) => state.post.showPreview;
export const selectModifyingPostData = (state) => state.post.modifyingPostContents;
export const selectAuthorNickname = (state) => state.post.PostData.author.nickname;
export const selectPostId = (state) => state.post.PostData.postId;