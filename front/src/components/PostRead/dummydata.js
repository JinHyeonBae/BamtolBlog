import shortid from 'shortid';

export const dummyDataForContents = {
  postId: shortid.generate(),
  title: "자습서: React 시작하기",
  subtitle: "이 자습서는 React에 대한 어떤 지식도 가정하지 않습니다.",
  contents: [
    {
      id: shortid.generate(),
      type: "header_1",
      text: "자습서를 시작하기 전에",
      children: [ 
        {
          id: shortid.generate(),
          type: "paragraph",
          text: "우리는 이 자습서에서 작은 게임을 만들겁니다. 게임을 만들고 싶지 않아서 자습서를 건너뛰고 싶을 수 있습니다. 그래도 한번 해보세요! 자습서를 통해 React 앱을 만드는 기본적인 사항들을 배웁니다. 이것을 마스터하면 React를 더 깊게 이해할 수 있습니다.",
          children: []
        },
        {
          id: shortid.generate(),
          type: "tip",
          text: "자습서는 실습으로 배우기를 선호하는 사람들에 맞춰 설계되었습니다. 기초부터 개념을 학습하길 선호한다면 단계별 가이드를 확인해보세요. 자습서와 단계별 가이드는 상호 보완적입니다.",
          children: []
        },
        {
          id: shortid.generate(),
          type: "paragraph",
          text: "자습서는 아래와 같이 몇 가지 부분으로 나뉘어 있습니다.",
          children: [
            {
              id: shortid.generate(),
              type: "unordered_list",
              text: "자습서 환경설정은 자습서를 따를 수 있는 시작점을 안내합니다.",
              children: []
            },
            {
              id: shortid.generate(),
              type: "unordered_list",
              text: "개요에서는 React의 기본(components, props, state)에 대해서 알아봅니다.",
              children: []
            },
            {
              id: shortid.generate(),
              type: "unordered_list",
              text: "게임 완성하기는 React 개발에서 사용하는 가장 일반적인 테크닉을 가르쳐 줄 것입니다.",
              children: []
            },
            {
              id: shortid.generate(),
              type: "unordered_list",
              text: " 시간여행 추가하기는 React의 고유한 강점에 대한 깊은 통찰력을 줄 것입니다.",
              children: []
            }
          ]
        },
        {
          id: shortid.generate(),
          type: "paragraph",
          text: "자습서를 익히기 위해 모든 부분을 한 번에 완료할 필요는 없습니다. 한두 구역이라도 가능한 한 많이 시도해 보세요.",
          children: []
        },
        {
          id: shortid.generate(),
          type: "paragraph",
          text: "이 자습서를 따라 하기 위해 코드를 복사하여 붙여넣는 것도 괜찮지만 직접 코드를 따라 적기를 추천합니다. 이 방식은 몸으로 기억하는 것과 더 강한 이해에 도움을 줄 것입니다.",
          children: []
        },
        {
          id: shortid.generate(),
          type: "header_2",
          text: "무엇을 만들게 될까요?",
          children: [
            {
              id: shortid.generate(),
              type: "paragraph",
              text: "우리는 React로 대화형 틱택토 게임을 만드는 방법을 알려드릴 것입니다.",
              children: []
            }
          ]
        },
        {
          id: shortid.generate(),
          type: "header_2",
          text: "필요한 선수 지식",
          children: [
            {
              id: shortid.generate(),
              type: "paragraph",
              text: "HTML과 JavaScript에 어느 정도 익숙하다고 가정하지만 다른 프로그래밍 언어를 사용하더라도 자습서를 따라갈 수 있습니다. 또한 함수, 객체, 배열, 가능하다면 클래스 같은 프로그래밍 개념에 익숙하다고 가정합니다.",
              children: []
            }
          ]
        },

      ]
    },
    {
      id: shortid.generate(),
      type: "header_1",
      text: "자습서를 위한 설정",
      children: [ 
        {
          id: shortid.generate(),
          type: "paragraph",
          text: "자습서를 완성하는 방법에는 두 가지가 있습니다. 브라우저에서 코드를 작성해도 되고 컴퓨터에 로컬 개발 환경을 설정해도 됩니다.",
          children: []
        },
        {
          id: shortid.generate(),
          type: "header_2",
          text: "선택 1: 브라우저에 코드 작성하기",
          children: [
            {
              id: shortid.generate(),
              type: "paragraph",
              text: "이 옵션은 가장 빠르게 시작하는 방식입니다!",
              children: []
            }
          ]
        },
        {
          id: shortid.generate(),
          type: "header_2",
          text: "선택 2: 자신의 컴퓨터에서 코드 작성하기",
          children: [
            {
              id: shortid.generate(),
              type: "paragraph",
              text: "이 방식은 완전히 선택사항이며 이 자습서에 필요한 것은 아닙니다!",
              children: []
            }
          ]
        }
      ]
    }
  ]
}

export const dummyDataForTOC = [
  {
    id: shortid.generate(),
    text: "자습서를 시작하기 전에",
    children: [
      {
        id: shortid.generate(),
        text: "무엇을 만들게 될까요?"
      },
      {
        id: shortid.generate(),
        text: "필요한 선수 지식"
      }
    ]
  },
  {
    id: shortid.generate(),
    text: "자습서를 위한 설정",
    children: [
      {
        id: shortid.generate(),
        text: "선택 1: 브라우저에 코드 작성하기"
      },
      {
        id: shortid.generate(),
        text: "선택 2: 자신의 컴퓨터에서 코드 작성하기"
      },
      {
        id: shortid.generate(),
        text: "도움이 필요할 때!"
      }
    ]
  },
  {
    id: shortid.generate(),
    text: "개요",
    children: []
  }
];
