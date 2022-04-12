import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import Axios from '../axiosConfig';
import shortid from 'shortid';

export const login = createAsyncThunk(
  "user/login",
  async (loginData) =>{
    const response = await Axios.post(`api/login`, loginData,  { withCredentials: true });
    return response.data;
  }
)

const initialState = {
  loginStatus: 'idle',
  user: {
    id: shortid.generate(),
    nickname: '',
  },
  token: ''
};

export const postSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    logout:(state) => {
      state.loginStatus = 'idle';
    },
  },
  extraReducers: builder => {
    builder.addCase(login.pending, (state)=> {
      state.loginStatus = 'loading';
    })
    builder.addCase(login.fulfilled, (state, {payload})=> {
      state.loginStatus = 'success';
      state.user = payload.user;
      state.token = payload.token;
    })
    builder.addCase(login.rejected, (state, action)=> {
      state.loginStatus = 'failed';
      state.error = action.payload;
    })
  }
});

export const { 
  logout,
} = postSlice.actions;
export default postSlice.reducer;

export const selectUser = (state) => state.user.user;
export const selectToken = (state) => state.user.token;
export const selectLoginStatus = (state) => state.user.loginStatus;

