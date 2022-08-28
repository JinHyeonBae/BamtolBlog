import React from 'react';
import Header from '../components/Header';
import SideBar from '../components/SideBar';
import Footer from '../components/Footer';
import { Breadcrumb, Layout as LayoutAntd } from 'antd';

const { Content } = LayoutAntd;

const Layout = ({ children }) => {
  return (
  <LayoutAntd>
    <Header />
    <LayoutAntd>
      <SideBar />
      <LayoutAntd
        style={{
          padding: '0 24px 24px',
        }}
      >
        <Breadcrumb
          style={{
            margin: '16px 0',
          }}
        >
          <Breadcrumb.Item>Home</Breadcrumb.Item>
          <Breadcrumb.Item>List</Breadcrumb.Item>
          <Breadcrumb.Item>App</Breadcrumb.Item>
        </Breadcrumb>
        <Content
          className="site-layout-background"
          style={{
            padding: 24,
            margin: 0,
            minHeight: 280,
          }}
        >
          { children }
        </Content>
      </LayoutAntd>
    </LayoutAntd>
    <Footer />
  </LayoutAntd>
  );
}

export default Layout;
