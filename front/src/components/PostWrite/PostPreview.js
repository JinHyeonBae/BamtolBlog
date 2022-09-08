import React, { useCallback } from 'react';
import { useDispatch } from 'react-redux';
import { changePostViewMode } from '../../_slices/postSlice';
import Post from './../PostRead/Post';
import { Button } from 'antd';

const PostPreview = () => {
  const dispatch = useDispatch();

  const changeViewMode = useCallback((mode)=>{
    dispatch(changePostViewMode(mode))
  }, [])

  return (
  <div className='PostPreview' >
    <div className='topButtons'>
      <Button className='leftButton' type='primary' onClick={()=>changeViewMode('save')}>저장</Button>
      <Button className='rightButton' onClick={()=>changeViewMode('modify')}>수정</Button>
    </div>
    <Post temporary={true}/>
  </div>
  )
}

export default PostPreview;