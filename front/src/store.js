import { configureStore } from '@reduxjs/toolkit';
import postReducer from './_slices/postSlice';
export const store = configureStore({
  reducer: {
    post: postReducer,
  },
});