import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Menu, Button } from 'antd';

const Header = () => {
  const navigate = useNavigate();

  const items = [
    { 
      label: (<Button type='link' onClick={()=>{navigate(`/`)}}>BotLine</Button>), 
      key: 'home'
    },
    { 
      label: (<Button type='primary' onClick={()=>{navigate(`/login`)}}>LogIn</Button>),
      key: 'login',
      style: { position: 'absolute', right: 0 }
    }
  ];

  return (
    <>
      <Menu mode="horizontal" items={items} />
    </>
  );
}

export default Header;
