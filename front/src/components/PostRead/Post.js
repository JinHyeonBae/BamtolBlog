import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useParams } from 'react-router';
import { useDispatch, useSelector } from 'react-redux';
import { loadPost, loadTempPostContents } from '../../_slices/postSlice';
import { selectPostData, selectLoadPostStatus, deletePost, selectDeletePostStatus } from '../../_slices/postSlice';
// import Contents from './Contents';

const Post = ({ temporary = false }) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const {postId} = useParams();
  const PostData = useSelector(selectPostData);
  const loadPostStatus = useSelector(selectLoadPostStatus);
  const deletePostStatus = useSelector(selectDeletePostStatus);

  useEffect(()=>{
    if(temporary){
      dispatch(loadTempPostContents());
    } else {
      dispatch(loadPost(postId));
    }
  },[])

  // useEffect(() => {
  //   if(loadPostStatus === 'success'){
  //     dispatch(makeIndexChartObject());
  //   }
  // }, [loadPostStatus])

  useEffect(() => {
    if(deletePostStatus === 'success'){
      navigate(``)
    }
  }, [loadPostStatus])

  const deletePostByPostId = () => {
    if(PostData?.title) {
      console.log("delete: ", postId);
      dispatch(deletePost(postId));
    }
  }

  return (
    <div className='Contents'>
      {
        loadPostStatus === 'success' || temporary === true
        ? <>
          <h1>{PostData?.title}</h1>
          {/* <h3>{PostData?.subtitle}</h3> */}
          {/* <Contents contents={PostData?.contents} /> */}
          <div>{PostData?.contents}</div>
          {PostData?.contents 
            && <div>
              <button>수정</button>
              <button onClick={deletePostByPostId()}>삭제</button> 
            </div>}
        </>
        : <> Loading... </>
      }
    </div>
  );
}

export default Post;