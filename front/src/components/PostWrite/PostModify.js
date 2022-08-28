import React, { useCallback } from 'react';
import { useDispatch } from 'react-redux';
import useInput from '../../hooks/useInput';
import { savePostData, changePostViewMode } from '../../_slices/postSlice';

const PostModify = () => {
  const dispatch = useDispatch();
  const [title, onChangeTitle] = useInput('');
  const [contents, onChangeContents] = useInput('');

  const changeViewMode = useCallback((mode)=>{
    //await
    dispatch(savePostData({
      title: title,
      contents: contents
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
    <div>
      <button onClick={()=>changeViewMode('save')}>저장</button>
      <button onClick={()=>changeViewMode('preview')}>미리보기</button>
    </div>
    <form>
      <div>
        <input id='title' value={title} onChange={onChangeTitle} />
      </div>
      <div>
        <textarea id='contents' value={contents} onChange={onChangeContents} />
      </div>
    </form>
   {/* <button onClick={makeNewBlockObject}>+</button> */}
  </div>
  )
}

export default PostModify;