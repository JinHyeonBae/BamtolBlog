import React, { useCallback } from 'react';
import { useDispatch } from 'react-redux';
import { changeViewModeToPreview } from '../../_slices/postSlice';
import Post from './../PostRead/Post';

const PostPreview = () => {
  const dispatch = useDispatch();

  const changeViewMode = useCallback(()=>{
    dispatch(changeViewModeToPreview(false))
  }, [])

  return (
  <div className='PostPreview' >
    <button onClick={changeViewMode}>수정</button>
    <Post temporary={true}/>
  </div>
  )
}

export default PostPreview;