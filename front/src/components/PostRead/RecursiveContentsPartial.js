import React, { Fragment } from 'react';
import ContentsPartial from './ContentsPartial';

const RecursiveContentsPartial = ({contentsObjects}) => {
  return (
    <>
      {contentsObjects?.map(child => (
        <Fragment key={child.id}>
          <ContentsPartial type={child.type} text={child.text} left={child?.left} right={child?.right} />
          <RecursiveContentsPartial contentsObjects={child?.children} />
        </Fragment>
        )
      )}
    </>
  )
}

export default RecursiveContentsPartial;