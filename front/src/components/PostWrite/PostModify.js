import React, { useState, useCallback } from 'react';
import { useDispatch } from 'react-redux';
import { CHANGE_VIEWMODE } from '../../_actions/types';
import { savePostContentsAction } from '../../_actions';
import PostForm from './PostForm';

const PostModify = () => {
  const dispatch = useDispatch();
  const [postContents, ] = useState('');  //object 형태

  const changeViewMode = useCallback(()=>{
    //await
    dispatch(savePostContentsAction(postContents, true));
    dispatch({
      type: CHANGE_VIEWMODE,
      data: true
    })
  }, [])

  return (
  <div className='PostModify'>
    <button onClick={changeViewMode}>미리보기</button>
    <PostForm />
  </div>
  )
}

export default PostModify;