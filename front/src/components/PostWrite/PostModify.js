import React, { useState, useCallback } from 'react';
import { useDispatch } from 'react-redux';
import { savePostContents, changePostViewMode } from '../../_slices/postSlice';
import shortid from 'shortid';

const PostModify = () => {
  const dispatch = useDispatch();
  const [postContents, setPostContents] = useState([]);  //object 형태

  const changeViewMode = useCallback((mode)=>{
    //await
    dispatch(savePostContents(postContents));
    dispatch(changePostViewMode(mode));
  }, [])

  const makeNewBlockObject = () => {
    const newBlockObject = {
      id: shortid.generate(),
      type: "paragraph",
      text: "",
      children: []
    }
    setPostContents([...postContents, newBlockObject]);
  }

  return (
  <div className='PostModify'>
    <div>
      <button onClick={()=>changeViewMode('save')}>저장</button>
      <button onClick={()=>changeViewMode('preview')}>미리보기</button>
    </div>
    <div className='postContentsBody' >
      {postContents && postContents?.map( el => (
        <div key={el.id} className="blockObject" type={el.type} contentEditable="true" suppressContentEditableWarning="true">
          {el.text}
        </div>
        )
      )}
    </div>
   <button onClick={makeNewBlockObject}>+</button>
  </div>
  )
}

export default PostModify;