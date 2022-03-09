import React from 'react';
import RecursiveContentsPartial from './RecursiveContentsPartial';

const Contents = ({contents}) => {

  return (
    <RecursiveContentsPartial contentsTree={contents} />
  );
}

export default Contents;