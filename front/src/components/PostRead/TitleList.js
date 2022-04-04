import React from 'react';
import { HashLink } from 'react-router-hash-link';
import { useSelector } from 'react-redux';
import { selectActiveId } from '../../_slices/postSlice';

const TitleList = ({ title }) => {
  const activeId = useSelector(selectActiveId);
  const active = activeId === title;

  return (
    <HashLink smooth to={`/post#${title}`}>
      {active && <span className='bookmark'></span>}
      <p className={active ? "active" : ""}>{title}</p>
    </HashLink>
  )
}

export default TitleList;