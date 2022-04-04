import React, { useState, useCallback } from 'react';
import { useDispatch } from 'react-redux';
import { changeViewModeToPreview, savePostContents } from '../../_slices/postSlice';
import shortid from 'shortid';

const PostModify = () => {
  const dispatch = useDispatch();
  const [postContents, setPostContents] = useState([]);  //object 형태

  const changeViewMode = useCallback(()=>{
    //await
    dispatch(savePostContents(postContents));
    dispatch(changeViewModeToPreview(true));
  }, [])

  const makeNewBlockObject = () => {
    const newBlockObject = {
      id: shortid.generate(),
      type: "paragraph",
      text: "",
      children: []
    }
    setPostContents([...postContents, newBlockObject]);
    console.log(postContents);
  }

  return (
  <div className='PostModify'>
    <button onClick={changeViewMode}>미리보기</button>
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