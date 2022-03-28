import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { savePostContentsAction } from '../../_actions';
import PostModify from './PostModify';
import PostPreview from './PostPreview';
import './PostWrite.scss';

const PostWrite = () => {
  const dispatch = useDispatch();
  const {showPreview, postContents} = useSelector((state)=>state.post);

  const onSubmitHandler = (e) => {
    e.preventDefault();
    dispatch(savePostContentsAction(postContents));
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