import React from 'react';
import RecursiveContentsPartial from './RecursiveContentsPartial';

const Contents = ({contents}) => {

  return (
    <RecursiveContentsPartial contentsObjects={contents} />
  );
}

export default Contents;