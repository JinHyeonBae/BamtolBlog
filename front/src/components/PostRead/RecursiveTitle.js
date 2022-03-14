import React from 'react';
import TitleList from './TitleList';

const RecursiveTitle = ( {objects} ) => {
  const createTitle = (children) => {
    return(
      <ul>
        {children.map((title)=>{
          return (
            <li key={title.id} className='title'>
              <TitleList title={title.text} />
              {title.children && createTitle(title.children)}
            </li>
          )
        })}
     </ul>
    )
  }
  return (createTitle(objects));
}

export default RecursiveTitle;