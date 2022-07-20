import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import Axios from '../axiosConfig';

export const login = createAsyncThunk(
  "user/login",
  async (loginData) =>{
    const response = await Axios.post(`auth/login`, loginData,  { withCredentials: true });
    return response.data;
  }
)
export const signup = createAsyncThunk(
  "user/signup",
  async (signupData) =>{
    console.log(signupData);
    const response = await Axios.post(`auth/signup`, signupData,  { withCredentials: true });
    return response.data;
  }
)

const initialState = {
  loginStatus: 'idle',
  signupStatus: 'idle',
  user: {
    id: null,
    nickname: '',
  },
  statusCode: '',
  message: '',
  messageCode: '',
  error: null
};

export const postSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    logout:(state) => {
      state.loginStatus = 'idle';
    },
    setSignupStatusIdle: (state) => {
      state.signupStatus = 'idle';
    }
  },
  extraReducers: builder => {
    builder.addCase(login.pending, (state)=> {
      state.loginStatus = 'loading';
    })
    builder.addCase(login.fulfilled, (state, {payload})=> {
      state.loginStatus = 'success';
      state.statusCode = payload.status.toString();
      state.message = payload?.message;
      state.user.id = payload.user?.userId;
      state.user.nickname = payload.user?.nickname;
    })
    builder.addCase(login.rejected, (state, action)=> {
      state.loginStatus = 'failed';
      state.error = action.payload;
    })
    builder.addCase(signup.pending, (state)=> {
      state.signupStatus = 'loading';
    })
    builder.addCase(signup.fulfilled, (state, { payload })=> {
      state.signupStatus = 'success';
      state.statusCode = payload.status.toString();
      state.message = payload?.message;
      state.messageCode = payload?.code;
    })
    builder.addCase(signup.rejected, (state, action)=> {
      state.signupStatus = 'failed';
      state.error = action.payload;
    })
  }
});

export const { 
  logout,
  setSignupStatusIdle,
} = postSlice.actions;
export default postSlice.reducer;

export const selectUser = (state) => state.user.user;
export const selectLoginStatus = (state) => state.user.loginStatus;
export const selectSignupStatus = (state) => state.user.signupStatus;
export const selectStatusCode = (state) => state.user.statusCode;
export const selectError = (state) => state.user.error;

