import React from 'react';
import Post from './Post';
import IndexChart from './IndexChart';
import './PostRead.scss';

const PostRead = () => {
  return (
    <div className='PostRead'>
      <Post />
      <IndexChart />
    </div>
  );
}

export default PostRead;