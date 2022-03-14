import React from 'react';
import { HashLink } from 'react-router-hash-link';
import { useSelector } from 'react-redux';

const TitleList = ({ title }) => {
  const { activeId } = useSelector((state)=> state.post);
  const active = activeId === title;

  return (
    <HashLink smooth to={`/post#${title}`}>
      {active && <span className='bookmark'></span>}
      <p className={active ? "active" : ""}>{title}</p>
    </HashLink>
  )
}

export default TitleList;