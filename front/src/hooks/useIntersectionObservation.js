import { useEffect, useRef } from 'react';
import { useDispatch } from 'react-redux';
import { changeActiveIdAction } from '../_actions';

const useIntersectionObservation = () => {
  const dispatch = useDispatch();
  const contentRef = useRef({});

  useEffect(() => {
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

    return () => observer.disconnect();
  }, []);
};

export default useIntersectionObservation;