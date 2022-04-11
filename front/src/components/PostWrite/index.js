import React from 'react';
import PostModify from './PostModify';
import PostPreview from './PostPreview';
import PostSave from './PostSave';
import './PostWrite.scss';
import { useSelector } from 'react-redux';
import { selectPostViewMode } from './../../_slices/postSlice';

const PostWrite = () => {
  const viewMode = useSelector(selectPostViewMode);

  return (<>
    <div className='PostWrite'>
      {viewMode == 'modify' && <PostModify/> }
      {viewMode == 'preview' && <PostPreview /> }
      {viewMode == 'save' && <PostSave /> }
    </div>
  </>)
}

export default PostWrite;