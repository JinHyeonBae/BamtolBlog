import React, { useEffect } from 'react';
import { useParams } from 'react-router';
import { useDispatch, useSelector } from 'react-redux';
import { loadPost, loadTempPostContents } from '../../_slices/postSlice';
import { selectPostData, selectLoadPostStatus, makeIndexChartObject } from '../../_slices/postSlice';
import Contents from './Contents';

const Post = ({ temporary = false }) => {
  const dispatch = useDispatch();
  const {userNickname, postsId} = useParams();
  const PostData = useSelector(selectPostData);
  const loadPostStatus = useSelector(selectLoadPostStatus);

  useEffect(()=>{
    if(temporary){
      dispatch(loadTempPostContents());
    } else {
      dispatch(loadPost({userNickname: userNickname, postsId: postsId}));
    }
  },[])

  useEffect(() => {
    if(loadPostStatus === 'success'){
      dispatch(makeIndexChartObject());
    }
  }, [loadPostStatus])

  return (
    <div className='Contents'>
      {
        loadPostStatus === 'success' || temporary === true
        ? <>
          <h1>{PostData?.title}</h1>
          <h3>{PostData?.subtitle}</h3>
          <Contents contents={PostData?.contents} />
        </>
        : <> Loading... </>
      }
    </div>
  );
}

export default Post;