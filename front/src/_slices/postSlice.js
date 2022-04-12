import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import Axios from '../axiosConfig';
import shortid from 'shortid';

export const loadPost = createAsyncThunk(
  "post/loadPost",
  async (loadPostData) =>{
    const response = await Axios.post(`api/getPostData`, loadPostData);
    return response.data;
  }
)
export const savePost = createAsyncThunk(
  "post/savePost",
  async (savePostData) =>{
    const response = await Axios.post(`api/savePostData`, savePostData);
    return response.data;
  }
)

const initialState = {
  loadPostStatus: 'idle',
  savePostDataStatus: 'idle',
  currPostId: '',
  activeId : '',
  postViewMode: 'modify',
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
    changePostViewMode:(state, action) => {
      state.postViewMode = action.payload;
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
    makeIndexChartObject: (state) => {
      let TOCData = [];
      state.PostData.contents.forEach((block_h1) => {
        if(block_h1.type.startsWith('header_')){
          let blockChildren_h2 = [];
          block_h1.children.forEach((block_h2) => {
            if(block_h2.type.startsWith('header_')){
              let blockChildren_h3 = [];
              block_h2.children.forEach((block_h3) => {
                if(block_h3.type.startsWith('header_')){
                  const blockObject_h3 = {
                    id: block_h3.id,
                    text: block_h3.text
                  };
                  blockChildren_h3.push(blockObject_h3);
                }
              });
              const blockObject_h2 = {
                id: block_h2.id,
                text: block_h2.text,
                children: blockChildren_h3
              };
              blockChildren_h2.push(blockObject_h2);
            }
          })
          const blockObject_h1 = {
            id: block_h1.id,
            text: block_h1.text,
            children: blockChildren_h2
          };
          TOCData.push(blockObject_h1);
        }
      });
      state.TOCData = TOCData;
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
    builder.addCase(savePost.pending, (state)=> {
      state.savePostDataStatus = 'loading';
    })
    builder.addCase(savePost.fulfilled, (state, {payload})=> {
      state.savePostDataStatus = 'success';
      state.currPostId = payload.postId;
    })
    builder.addCase(savePost.rejected, (state, action)=> {
      state.savePostDataStatus = 'failed';
      state.error = action.payload;
    })
  }
})

export const { 
  addValue, 
  changePostViewMode,
  savePostContents,
  setActiveId, 
  loadTempPostContents,
  makeIndexChartObject, 
} = postSlice.actions;

export default postSlice.reducer;

export const selectLoadPostStatus = (state) => state.post.loadPostStatus;
export const selectActiveId = (state) => state.post.activeId;
export const selectPostData = (state) => state.post.PostData;
export const selectPostTOC = (state) => state.post.TOCData;
export const selectPostViewMode = (state) => state.post.postViewMode;
export const selectModifyingPostData = (state) => state.post.modifyingPostContents;
export const selectAuthorNickname = (state) => state.post.PostData.author.nickname;
export const selectPostId = (state) => state.post.PostData.postId;
export const selectCurrPostId = (state) => state.post.currPostId;
export const selectSavePostDataStatus = (state) => state.post.savePostDataStatus;