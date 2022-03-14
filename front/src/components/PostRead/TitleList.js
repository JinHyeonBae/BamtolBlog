import React from 'react';
import { HashLink } from 'react-router-hash-link';

const TitleList = ({title, hash, activeId}) => {
  const active = (decodeURIComponent(hash) === title || activeId === title);
  return (
    <HashLink smooth to={`/post#${title}`}>
      {active && <span className='bookmark'></span>}
      <p className={active ? "active" : ""}>{title}</p>
    </HashLink>
  )
}

export default TitleList;