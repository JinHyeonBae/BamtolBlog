import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { loadPostContents, loadTempPostContents } from '../../_slices/postSlice';
import { selectPostData } from '../../_slices/postSlice';
import Contents from './Contents';

const Post = ({ temporary = false }) => {
  const dispatch = useDispatch();
  const PostData = useSelector(selectPostData);

  useEffect(()=>{
    if(temporary){
      dispatch(loadTempPostContents());
    } else {
      dispatch(loadPostContents());
    }
  },[])

  return (
    <div className='Contents'>
      <h1>{PostData?.title}</h1>
      <h3>{PostData?.subtitle}</h3>
      <Contents contents={PostData?.contents} />
    </div>
  );
}

export default Post;