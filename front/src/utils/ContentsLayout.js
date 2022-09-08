import React from 'react';
import { Layout as LayoutAntd } from 'antd';

const { Content } = LayoutAntd;

const ContentsLayout = ({ children }) => {

  return (
    <LayoutAntd.Content
      style={{
        padding: '0 24px 24px',
      }}
    >
      <Content
        style={{
          padding: 24,
          margin: 0,
          minHeight: 280,
        }}
      >
        { children }
      </Content>
    </LayoutAntd.Content>
  );
}

export default ContentsLayout;
