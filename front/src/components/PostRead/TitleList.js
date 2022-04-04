import React from 'react';
import { HashLink } from 'react-router-hash-link';
import { useSelector } from 'react-redux';
import { selectActiveId, selectAuthorNickname, selectPostId } from '../../_slices/postSlice';

const TitleList = ({ title }) => {
  const activeId = useSelector(selectActiveId);
  const authorNickname = useSelector(selectAuthorNickname);
  const postId = useSelector(selectPostId);
  const active = activeId === title;

  return (
    <HashLink smooth to={`/${authorNickname}/posts/${postId}#${title}`}>
      {active && <span className='bookmark'></span>}
      <p className={active ? "active" : ""}>{title}</p>
    </HashLink>
  )
}

export default TitleList;