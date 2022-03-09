import React, { Fragment } from 'react';
import ContentsPartial from './ContentsPartial';

const RecursiveContentsPartial = ({contentsTree}) => {
  const createContentsPartial = (child) =>{
    return(
      child.children && (
        <>
        <ContentsPartial type={child.type} text={child.text} />
          {child.children?.map(child =>
            <Fragment key={child.id}> {createContentsPartial(child)} </Fragment>
          )}  
        </>
      )
    )
  };
  return (
    <div>
      {contentsTree?.map(child => 
        <Fragment key={child.id}>{createContentsPartial(child)}</Fragment>
      )}
    </div>
  )
}

export default RecursiveContentsPartial;