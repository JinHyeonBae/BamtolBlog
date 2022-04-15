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
    const response = await Axios.post(`api/signup`, signupData,  { withCredentials: true });
    return response.data;
  }
)
export const checkEmailDuplicate = createAsyncThunk(
  "user/checkEmailDuplicate",
  async (email) => {
    const response = await Axios.post(`api/checkEmailDuplicate`, {"email": email});
    return response.data;
  }
)
export const checkNicknameDuplicate = createAsyncThunk(
  "user/checkNicknameDuplicate",
  async (nickname) => {
    const response = await Axios.post(`api/checkNicknameDuplicate`, {"nickname": nickname});
    return response.data;
  }
)


const initialState = {
  loginStatus: 'idle',
  signupStatus: 'idle',
  emailDuplicateStatus: '',
  nicknameDuplicateStatus: '',
  emailNotDuplication: true,
  nicknameNotDuplication: true,
  user: {
    id: null,
    nickname: '',
  },
  token: '',
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
    setEmailDuplicateStatusIdle: (state) => {
      state.emailDuplicateStatus = 'idle';
    },
    setNicknameDuplicateStatusIdle: (state) => {
      state.nicknameDuplicateStatus = 'idle';
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
    })
    builder.addCase(checkEmailDuplicate.pending, (state)=> {
      state.emailDuplicateStatus = 'loading';
    })
    builder.addCase(checkEmailDuplicate.fulfilled, (state, {payload})=> {
      state.emailDuplicateStatus = 'success';
      state.emailNotDuplication = payload.success;
    })
    builder.addCase(checkEmailDuplicate.rejected, (state, action)=> {
      state.emailDuplicateStatus = 'failed';
      state.error = action.payload;
    })
    builder.addCase(checkNicknameDuplicate.pending, (state)=> {
      state.nicknameDuplicateStatus = 'loading';
    })
    builder.addCase(checkNicknameDuplicate.fulfilled, (state, {payload})=> {
      state.nicknameDuplicateStatus = 'success';
      state.nicknameNotDuplication = payload.success;
    })
    builder.addCase(checkNicknameDuplicate.rejected, (state, action)=> {
      state.nicknameDuplicateStatus = 'failed';
      state.error = action.payload;
    })
  }
});

export const { 
  logout,
  setSignupStatusIdle,
  setEmailDuplicateStatusIdle,
  setNicknameDuplicateStatusIdle
} = postSlice.actions;
export default postSlice.reducer;

export const selectUser = (state) => state.user.user;
export const selectToken = (state) => state.user.token;
export const selectLoginStatus = (state) => state.user.loginStatus;
export const selectSignupStatus = (state) => state.user.signupStatus;
export const selectEmailDuplicateStatus = (state) => state.user.emailDuplicateStatus;
export const selectEmailNotDuplication = (state) => state.user.emailNotDuplication;
export const selectNicknameDuplicateStatus = (state) => state.user.nicknameDuplicateStatus;
export const selectNicknameNotDuplication = (state) => state.user.nicknameNotDuplication;

