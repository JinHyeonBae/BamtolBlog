import React from 'react';
import { useSelector } from 'react-redux';
import { selectUser } from '../_slices/userSlice';
import Header from '../components/Header';
import Footer from '../components/Footer';
import SideBar from '../components/SideBar';
import ContentsLayout from './ContentsLayout';
import { Layout as LayoutAntd } from 'antd';

const Layout = ({ children }) => {
  const loggedInUser = useSelector(selectUser).id;

  return (
  <>
    <Header />
    <LayoutAntd>
      { loggedInUser && <SideBar /> }
      <ContentsLayout>
        { children }
      </ContentsLayout>
    </LayoutAntd>
    <Footer />
  </>
  );
}

export default Layout;
