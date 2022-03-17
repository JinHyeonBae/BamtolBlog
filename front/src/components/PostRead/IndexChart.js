import React, { useEffect, useRef } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { LOAD_POST_TOC } from '../../_actions/types';
import { changeActiveIdAction } from '../../_actions';
import RecursiveTitle from './RecursiveTitle';

const IndexChart = () => {
  const dispatch = useDispatch();
  const contentRef = useRef({});
  const { TOCData } = useSelector((state)=>state.post);

  useEffect(()=>{
    dispatch({
      type: LOAD_POST_TOC
    });
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
      if(visibleContent.length) dispatch(changeActiveIdAction(visibleContent[0].target.id));
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