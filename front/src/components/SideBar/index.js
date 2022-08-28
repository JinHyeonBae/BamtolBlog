import React from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { selectUser } from '../../_slices/userSlice';
import { Layout as LayoutAntd, Menu, Input, Button } from 'antd';
import { LaptopOutlined, UserOutlined, NotificationOutlined, PlusSquareFilled } from '@ant-design/icons';
import styled from 'styled-components';

const { Sider } = LayoutAntd;

const StyledInputSearch = styled(Input.Search)`
  vertical-align: middle;
`;

const onClickWriteButton = () => {
  const navigate = useNavigate();
  const nickname = useSelector(selectUser)?.nickname;
  navigate(`/${nickname}/posts/write`);
}

const items = [
  {
    key: `Search`,
    label: (<StyledInputSearch placeholder="search" allowClear />)
  },
  {
    key: `Web`,
    icon: React.createElement(LaptopOutlined),
    label: `Web`,
    children: [
      {
        key: `Modern Javascript`,
        label: `Modern Javascript`
      },
      {
        key: `React.js`,
        label: `React.js`
      },
    ]
  },
  {
    key: `Algorithm`,
    icon: React.createElement(UserOutlined),
    label: `Algorithm`,
    children: [
      {
        key: `Graph`,
        label: `Graph`
      },
      {
        key: `Math`,
        label: `Math`
      },
    ]
  },
  {
    key: `Study`,
    icon: React.createElement(NotificationOutlined),
    label: `Study`,
    children: [
      {
        key: `CS`,
        label: `CS`
      }
    ]
  },
  { 
    key: 'write',
    label: (<Button type='link' onClick={onClickWriteButton}  style={{width: "150px"}}><PlusSquareFilled  style={{fontSize: "25px"}}/></Button>),
    disabled: true
  }
];

const SideBar = () => {
  return (
      <Sider width={200} className="site-layout-background">
        <Menu
          mode="inline"
          defaultSelectedKeys={['Web']}
          defaultOpenKeys={['Web']}
          style={{
            height: '100%',
            borderRight: 0,
          }}
          items={items}
        />
      </Sider>
  );
}

export default SideBar;
