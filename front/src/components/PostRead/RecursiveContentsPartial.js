import React, { Fragment } from 'react';
import ContentsPartial from './ContentsPartial';

const RecursiveContentsPartial = ({contentsObjects}) => {
  return (
    <>
      {contentsObjects?.map(child => (
        <Fragment key={child.id}>
          <ContentsPartial type={child.type} text={child.text} />
          <RecursiveContentsPartial contentsObjects={child?.children} />
        </Fragment>
        )
      )}
    </>
  )
}

export default RecursiveContentsPartial;