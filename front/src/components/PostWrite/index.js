import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import PostModify from './PostModify';
import PostPreview from './PostPreview';
import { savePostContents, selectShowPreview, selectModifyingPostData } from '../../_slices/postSlice';
import './PostWrite.scss';

const PostWrite = () => {
  const dispatch = useDispatch();
  const showPreview = useSelector(selectShowPreview);
  const postContents = useSelector(selectModifyingPostData);

  const onSubmitHandler = (e) => {
    e.preventDefault();
    dispatch(savePostContents(postContents));
  }

  return (<>
    <div className='PostWrite'>
      <div>
        <button onClick={onSubmitHandler}>저장</button>
      </div>
      { showPreview
        ? <PostPreview />
        : <PostModify />
      }
    </div>
  </>)
}

export default PostWrite;