import React, { useCallback } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import useInput from '../../hooks/useInput';
import { savePostData, changePostViewMode, selectModifyingPostData } from '../../_slices/postSlice';
import { Input, Button } from 'antd';

const PostModify = () => {
  //미리보기 이후 돌아왔을때를 위해 데이터 받아오기
  const modifyingPost = useSelector(selectModifyingPostData);
  const dispatch = useDispatch();
  const [title, onChangeTitle] = useInput(modifyingPost.title);
  const [contents, onChangeContents] = useInput(modifyingPost.contents);

  const changeViewMode = useCallback((mode)=>{
    dispatch(savePostData({
      title: title,
      contents: contents,
      displayLevel: modifyingPost.displayLevel,
      price: modifyingPost.price,
    }));
    dispatch(changePostViewMode(mode));
  }, [title, contents]);

  // const makeNewBlockObject = () => {
  //   const newBlockObject = {
  //     id: shortid.generate(),
  //     type: "paragraph",
  //     text: "",
  //     children: []
  //   }
  //   setPostContents([...postContents, newBlockObject]);
  // }

  return (
  <div className='PostModify'>
    <div className='topButtons'>
      <Button className='leftButton' type='primary' onClick={()=>changeViewMode('save')}>저장</Button>
      <Button className='rightButton' onClick={()=>changeViewMode('preview')}>미리보기</Button>
    </div>
    <form>
      <div id='PostModify-title'>
        <Input id='title' value={title} onChange={onChangeTitle} />
      </div>
      <div id='PostModify-contents'>
        <Input.TextArea id='contents' value={contents} onChange={onChangeContents} />
      </div>
    </form>
   {/* <button onClick={makeNewBlockObject}>+</button> */}
  </div>
  )
}

export default PostModify;