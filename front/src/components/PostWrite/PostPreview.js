import React, { useCallback } from 'react';
import { useDispatch } from 'react-redux';
import { changePostViewMode } from '../../_slices/postSlice';
import Post from './../PostRead/Post';

const PostPreview = () => {
  const dispatch = useDispatch();

  const changeViewMode = useCallback((mode)=>{
    dispatch(changePostViewMode(mode))
  }, [])

  return (
  <div className='PostPreview' >
    <div>
      <button onClick={()=>changeViewMode('save')}>저장</button>
      <button onClick={()=>changeViewMode('modify')}>수정</button>
    </div>
    <Post temporary={true}/>
  </div>
  )
}

export default PostPreview;