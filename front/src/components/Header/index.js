import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { logout, selectUser } from '../../_slices/userSlice';
import { Menu, Button } from 'antd';

const Header = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const nickname = useSelector(selectUser)?.nickname;

  const items = [
    { 
      label: (<Button type='link' onClick={()=>{navigate(`/`)}}>BotLine</Button>), 
      key: 'home'
    },
    { 
      label: (<Button type='link' onClick={()=>{navigate(`/login`)}}>LogIn</Button>),
      key: 'login'
    },
    { 
      label: (<Button type='link' onClick={()=>{dispatch(logout())}}>LogOut</Button>),
      key: 'logout'
    },
    { 
      label: (<Button type='link' onClick={()=>{navigate(`/signup`)}}>SignUp</Button>),
      key: 'signup'
    },
    { 
      label: (<Button type='link' onClick={()=>{navigate(`/${nickname}/posts/write`)}}>Write</Button>),
      key: 'write',
      children: []
    }
  ];

  return (
    <>
      <Menu mode="horizontal" items={items} style={{ float: "right" }}/>
    </>
  );
}

export default Header;
