import React, { useEffect, useRef } from 'react';
import { useParams } from 'react-router';
import { useDispatch, useSelector } from 'react-redux';
import { loadPostTOC, setActiveId, selectPostTOC } from '../../_slices/postSlice';
import RecursiveTitle from './RecursiveTitle';

const IndexChart = () => {
  const dispatch = useDispatch();
  const contentRef = useRef({});
  const {userNickname, postsId} = useParams();
  const TOCData = useSelector(selectPostTOC);

  useEffect(()=>{
    dispatch(loadPostTOC({userNickname: userNickname, postsId: postsId}));
  },[])
  
  useEffect(()=>{
    createIntersectionObservation();
  },[TOCData]);

  const createIntersectionObservation = () => {
    const option = { threshold: 1.0 };
    const callback = ( observedContent ) => {
      observedContent.forEach((content) => {
        contentRef.current[content.target.id] = content;
      });
      const visibleContent = Object.values(contentRef.current).filter(
        (content) => content.isIntersecting
      );
      if(visibleContent.length) dispatch(setActiveId(visibleContent[0].target.id));
    };

    const observer = new IntersectionObserver(callback, option);

    const contents = [...document.querySelectorAll('[class^="header_"]')];
    contents.forEach((content) => observer.observe(content));
  }

  return (
    <div className='IndexChart'>
      <div>
        <div>
          자습서
          <span className="svgImg">V</span>
        </div>
        <RecursiveTitle titleObjects={ TOCData } />
      </div>
    </div>
  );
}

export default IndexChart;