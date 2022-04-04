import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams } from 'react-router';
import { useNavigate } from 'react-router-dom';
import PostModify from './PostModify';
import PostPreview from './PostPreview';
import { savePost, selectShowPreview, selectModifyingPostData, selectCurrPostId, selectSavePostDataState } from '../../_slices/postSlice';
import './PostWrite.scss';

const PostWrite = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { userNickname } = useParams();
  const showPreview = useSelector(selectShowPreview);
  const postContents = useSelector(selectModifyingPostData);
  const savePostDataState = useSelector(selectSavePostDataState);
  const currPostId = useSelector(selectCurrPostId);

  const onSubmitHandler = (e) => {
    e.preventDefault();
    const body = {
      postContents: postContents,
      token: 'tokentoken'
    }
    dispatch(savePost(body));
  }
  
  useEffect(()=>{
    if(savePostDataState === 'success') {
      navigate(`/${userNickname}/posts/${currPostId}`);
    }
  },[savePostDataState])

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