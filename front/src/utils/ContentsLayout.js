import React from 'react';
import { useSelector } from 'react-redux';
import { selectUser } from '../_slices/userSlice';
import { Breadcrumb, Layout as LayoutAntd } from 'antd';

const { Content } = LayoutAntd;

const ContentsLayout = ({ children }) => {
  const loggedInUser = useSelector(selectUser).id;

  return (
    <LayoutAntd.Content
      style={{
        padding: '0 24px 24px',
      }}
    >
    {/* 게시글 트리의 Breadcrumb */}
    { loggedInUser &&
      <Breadcrumb
        style={{
          margin: '16px 0',
        }}
      >
        <Breadcrumb.Item>Home</Breadcrumb.Item>
        <Breadcrumb.Item>List</Breadcrumb.Item>
        <Breadcrumb.Item>App</Breadcrumb.Item>
      </Breadcrumb>
    }
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
