import React, { useState } from 'react';
import Contents from './Contents';
import { dummyDataForContents } from './dummydata';

const Post = () => {
  const [PostData, ] = useState(dummyDataForContents);

  return (
    <div className='Contents'>
      <h1>{PostData.title}</h1>
      <h3>{PostData.subtitle}</h3>
      <Contents contents={PostData.contents} />
    </div>
  );
}

export default Post;