import React, { useEffect, useCallback } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams } from 'react-router';
import { useNavigate } from 'react-router-dom';
import { changePostViewMode, savePost, selectModifyingPostData, selectSavePostDataStatus, selectCurrPostId, selectStatusCode } from '../../_slices/postSlice';
import { selectUser } from '../../_slices/userSlice';
import useInput from '../../hooks/useInput';

const PostSave = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { userNickname } = useParams();

  const postData = useSelector(selectModifyingPostData);
  const savePostDataStatus = useSelector(selectSavePostDataStatus);
  const currPostId = useSelector(selectCurrPostId);
  const userId = useSelector(selectUser).id;
  const statusCode = useSelector(selectStatusCode);
  
  const [displayLevel, onChangeDisplayLevel] = useInput('public');
  const [price, onChangePrice] = useInput(0);
  
  useEffect(()=>{
    if (savePostDataStatus === 'success'){
      if(statusCode == '200') {
        alert("성공");
        navigate(`/${userNickname}/posts/${currPostId}`);
      } else {
        alert("문제 발생! / 이미 등록된 게시글입니다.");
      }
    } else if (savePostDataStatus === 'failed') {
      alert("저장에 실패했습니다. 다시 시도해주세요.");
    }
  },[savePostDataStatus])

  const changeViewMode = useCallback((mode)=>{
    dispatch(changePostViewMode(mode))
  }, [])
  
  const onSubmitHandler = (e) => {
    e.preventDefault();
    const body = {
      title: postData.title,
      displayLevel: displayLevel,
      price: price,
      userId: userId,
      contents: postData.contents
    }
    dispatch(savePost(body));
  }
  
  return (
    <div>
      <button onClick={()=>changeViewMode('modify')}>X</button>
      <form onSubmit={onSubmitHandler}>
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