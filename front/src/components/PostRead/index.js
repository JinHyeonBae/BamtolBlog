import React from 'react';
import { useSelector } from 'react-redux';
import Post from './Post';
import IndexChart from './IndexChart';
import { selectUser } from '../../_slices/userSlice';
import { Breadcrumb} from 'antd';
import './PostRead.scss';

const PostRead = () => {
  const loggedInUser = useSelector(selectUser).id;
  
  return (
    <div className='PostRead'>
      {/* 게시글 트리의 Breadcrumb */}
      { loggedInUser &&
        <Breadcrumb
          style={{
            margin: '16px 0',
          }}
        >
          <Breadcrumb.Item>Home</Breadcrumb.Item>
          <Breadcrumb.Item>List</Breadcrumb.Item>
          <Breadcrumb.Item>App</Breadcrumb.Item>
        </Breadcrumb>
      }
      <Post />
      <IndexChart />
    </div>
  );
}

export default PostRead;