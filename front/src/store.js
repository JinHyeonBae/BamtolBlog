import { configureStore } from '@reduxjs/toolkit';
import postReducer from './_slices/postSlice';
import userReducer from './_slices/userSlice';
export const store = configureStore({
  reducer: {
    post: postReducer,
    user: userReducer,
  },
});