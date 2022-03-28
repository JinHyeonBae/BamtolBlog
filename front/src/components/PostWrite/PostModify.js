import React, { useState, useCallback } from 'react';
import { useDispatch } from 'react-redux';
import { CHANGE_VIEWMODE } from '../../_actions/types';
import { savePostContentsAction } from '../../_actions';
import shortid from 'shortid';

const PostModify = () => {
  const dispatch = useDispatch();
  const [postContents, setPostContents] = useState([]);  //object 형태

  const changeViewMode = useCallback(()=>{
    //await
    dispatch(savePostContentsAction(postContents, true));
    dispatch({
      type: CHANGE_VIEWMODE,
      data: true
    })
  }, [])

  // const onChangeBlockObject = () => {
  //   console.log(postContents);
  // }

  const makeNewBlockObject = () => {
    // const target = document.getElementsByClassName('postContentsBody')[0];
    // let div = document.createElement('div');
    // div.setAttribute('className', 'blockObject');
    // div.setAttribute('contentEditable', 'true');
    // div.addEventListener('ValueChange', onChangeBlockObject);
    // target.append(div);
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