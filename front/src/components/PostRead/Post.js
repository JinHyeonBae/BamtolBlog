import React, { useState } from 'react';
import Contents from './Contents';
import { dummyData } from './dummydata';

const Post = () => {
  const [PostData, ] = useState(dummyData);

  return (
    <div className='Contents'>
      <h1>{PostData.title}</h1>
      <h3>{PostData.subtitle}</h3>
      <Contents contents={PostData.contents} />
    </div>
  );
}

export default Post;