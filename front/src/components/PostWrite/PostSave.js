import React, { useEffect, useCallback } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams } from 'react-router';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import { changePostViewMode, savePost, selectModifyingPostData, selectSavePostDataState, selectCurrPostId } from '../../_slices/postSlice';
import { selectUser } from '../../_slices/userSlice';
import useInput from '../../hooks/useInput';

const PostSave = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { userNickname } = useParams();

  const postData = useSelector(selectModifyingPostData);
  const savePostDataState = useSelector(selectSavePostDataState);
  const currPostId = useSelector(selectCurrPostId);
  const user = useSelector(selectUser);
  
  const [title, onChangeTitle] = useInput('');
  const [displayLevel, onChangeDisplayLevel] = useInput('public');
  const [price, onChangePrice] = useInput(0);
  
  useEffect(()=>{
    if(savePostDataState === 'success') {
      navigate(`/${userNickname}/posts/${currPostId}`);
    }
  },[savePostDataState])

  const changeViewMode = useCallback((mode)=>{
    dispatch(changePostViewMode(mode))
  }, [])
  
  const onSubmitHandler = (e) => {
    e.preventDefault();
    const postDataToSave = {
      header: {
        token : Cookies.get('token')
      },
      body : {
        title: title,
        displayLevel: displayLevel,
        price: price,
        author: user,
        postContents: postData,
      }
    }
    dispatch(savePost(postDataToSave));
  }
  
  return (
    <div>
      <button onClick={()=>changeViewMode('modify')}>X</button>
      <form onSubmit={onSubmitHandler}>
        <div>
          <label htmlFor='title'>제목</label>
          <input id='title' name='title' type='text' value={title} onChange={onChangeTitle} />
        </div>
        <div onChange={onChangeDisplayLevel} >
          <label htmlFor='displayLevel'>공개여부</label>
          <input id="displayLevel" name='displayLevel' type='radio' value='public' defaultChecked/> public
          <input id="displayLevel" name='displayLevel' type='radio' value='protect' /> protect
          <input id="displayLevel" name='displayLevel' type='radio' value='private' /> private
        </div>
        <div>
          <label htmlFor='price'>가격</label>
          <input id="price" name='price' type='number' value={price} onChange={onChangePrice} />
        </div>
        <button type='submit'>등록</button>
      </form>
    </div>
  )
}

export default PostSave;