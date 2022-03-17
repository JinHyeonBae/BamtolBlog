import React from 'react';
import TitleList from './TitleList';

const RecursiveTitle = ( {titleObjects} ) => {
  return (
    <ul>
      {titleObjects?.map((title)=>{
        return (
          <li key={title.id} className='title'>
            <TitleList title={title.text} />
            <RecursiveTitle titleObjects={title?.children} />
          </li>
        )
      })}
    </ul>
  );
}

export default RecursiveTitle;