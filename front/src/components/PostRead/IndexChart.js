import React from 'react';
import TitleList from './TitleList';
import useIntersectionObservation from './useIntersectionObservation';

const dummyData = [
  {
    id: 1,
    title: "자습서를 시작하기 전에",
    childTitles: [
      {
        id: 101,
        title: "무엇을 만들게 될까요?"
      },
      {
        id: 102,
        title: "필요한 선수 지식"
      }
    ]
  },
  {
    id: 2,
    title: "자습서를 위한 설정",
    childTitles: [
      {
        id: 201,
        title: "선택 1: 브라우저에 코드 작성하기"
      },
      {
        id: 202,
        title: "선택 2: 자신의 컴퓨터에서 코드 작성하기"
      },
      {
        id: 203,
        title: "도움이 필요할 때!"
      }
    ]
  },
  {
    id: 3,
    title: "개요",
    childTitles: []
  }
];

const IndexChart = () => {
  useIntersectionObservation();

  return (
    <div className='IndexChart'>
      <div>
        <div>
          자습서
          <span className="svgImg">V</span>
        </div>
        <ul>
        {dummyData.map((title)=>{
          if(title.childTitles.length) {
            return (
              <li key={title.id} className='title'>
                <TitleList title={title.title} />
                <ul>
                  {title.childTitles.map((childTitle) => 
                    <li key={childTitle.id} className='title'>
                      <TitleList title={childTitle.title}/>
                    </li>
                  )}
                </ul>
              </li>
            )
          }
          return(<li key={title.id} className='title'><TitleList title={title.title} /></li>)
        })}
        </ul>
      </div>
    </div>
  );
}

export default IndexChart;