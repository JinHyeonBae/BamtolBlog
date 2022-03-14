import { useEffect, useRef } from 'react';

const useIntersectionObservation = (setActiveId) => {
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
      setActiveId(visibleContent[0].target.id);
    };
    const observer = new IntersectionObserver(callback, option);

    const contents = [...document.querySelectorAll('[class^="header_"]')];

    contents.forEach((content) => observer.observe(content));

    return () => observer.disconnect();
  }, [setActiveId]);
};

export default useIntersectionObservation;