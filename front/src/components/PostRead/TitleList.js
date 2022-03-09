import React from 'react';
import { HashLink } from 'react-router-hash-link';

const TitleList = ({title, hash}) => {
  console.log(title, decodeURIComponent(hash), decodeURIComponent(hash)==title);
  return (
    <HashLink smooth to={`/post#${title}`}>
      {decodeURIComponent(hash)===title && <span className='bookmark'></span>}
      <h5>{title}</h5>
    </HashLink>
  )
}

export default TitleList;