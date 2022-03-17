import React, { useCallback } from 'react';
import { useDispatch } from 'react-redux';
import { CHANGE_VIEWMODE } from '../../_actions/types';
import Post from './../PostRead/Post';

const PostPreview = () => {
  const dispatch = useDispatch();

  const changeViewMode = useCallback(()=>{
    dispatch({
      type: CHANGE_VIEWMODE,
      data: false
    })
  }, [])

  return (
  <div className='PostPreview' >
    <button onClick={changeViewMode}>수정</button>
    <Post temporary={true}/>
  </div>
  )
}

export default PostPreview;