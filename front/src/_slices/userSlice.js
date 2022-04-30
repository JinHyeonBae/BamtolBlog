import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import Axios from '../axiosConfig';

export const login = createAsyncThunk(
  "user/login",
  async (loginData, { rejectWithValue }) =>{
    try {
      const response = await Axios.post(`auth/login`, loginData,  { withCredentials: true });
      return response.data;
    } catch (err) {
      let error = err;
      if(!error.response){
        throw err;
      }
      return rejectWithValue(error.response.data);
    }
  }
)
export const signup = createAsyncThunk(
  "user/signup",
  async (signupData, { rejectWithValue }) =>{
    try {
      const response = await Axios.post(`auth/signup`, signupData,  { withCredentials: true });
      return response.data;
    } catch (err) {
      let error = err;
      if(!error.response){
        throw err;
      }
      return rejectWithValue(error.response.data);
    }
  }
)

const initialState = {
  loginStatus: 'idle',
  signupStatus: 'idle',
  emailDuplication: false,
  nicknameDuplication: false,
  user: {
    id: null,
    nickname: '',
  },
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
    },
    initEmailDuplicate: (state) => {
      state.emailDuplication = false;
    },
    initNicknameDuplicate: (state) => {
      state.nicknameDuplication = false;
    }
  },
  extraReducers: builder => {
    builder.addCase(login.pending, (state)=> {
      state.loginStatus = 'loading';
    })
    builder.addCase(login.fulfilled, (state, {payload})=> {
      state.loginStatus = 'success';
      state.user = payload.user;
    })
    builder.addCase(login.rejected, (state, action)=> {
      state.loginStatus = 'failed';
      state.error = action.payload;
    })
    builder.addCase(signup.pending, (state)=> {
      state.signupStatus = 'loading';
    })
    builder.addCase(signup.fulfilled, (state)=> {
      state.signupStatus = 'success';
    })
    builder.addCase(signup.rejected, (state, action)=> {
      state.signupStatus = 'failed';
      state.error = action.payload;
      state.emailDuplication = action.payload.emailDuplicated;
      state.nicknameDuplication = action.payload.nicknameDuplicated;
    })
  }
});

export const { 
  logout,
  setSignupStatusIdle,
  initEmailDuplicate,
  initNicknameDuplicate
} = postSlice.actions;
export default postSlice.reducer;

export const selectUser = (state) => state.user.user;
export const selectLoginStatus = (state) => state.user.loginStatus;
export const selectSignupStatus = (state) => state.user.signupStatus;
export const selectEmailDuplication = (state) => state.user.emailDuplication;
export const selectNicknameDuplication = (state) => state.user.nicknameDuplication;
export const selectError = (state) => state.user.error;

