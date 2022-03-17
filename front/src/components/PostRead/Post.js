import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { LOAD_POST_CONTENTS, LOAD_TEMPPOST_CONTENTS } from '../../_actions/types';
import Contents from './Contents';

const Post = ({ temporary = false }) => {
  const dispatch = useDispatch();
  const { PostData } = useSelector((state)=>state.post);

  useEffect(()=>{
    if(temporary){
      dispatch({
        type: LOAD_TEMPPOST_CONTENTS
      })
    } else {
      dispatch({
        type: LOAD_POST_CONTENTS
      })
    }
  },[])

  return (
    <div className='Contents'>
      <h1>{PostData?.title}</h1>
      <h3>{PostData?.subtitle}</h3>
      <Contents contents={PostData?.contents} />
    </div>
  );
}

export default Post;