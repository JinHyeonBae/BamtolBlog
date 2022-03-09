import React from 'react';

const ContentsPartial = ({type, text}) => {
  switch(type){
    case "header_1":
      return <h1 className='header_1' id={text}>{text}</h1>;
    case "header_2":
      return <h3 className='header_2' id={text}>{text}</h3>;
    case "header_3":
      return <h5 className='header_3' id={text}>{text}</h5>;
    case "paragraph":
      return <p>{text}</p>;
    case "tip":
      return <div className='tip'>{text}</div>;
    case "unordered_list":
      return <p> - {text}</p>;
    default: return;
  }
}

export default ContentsPartial;