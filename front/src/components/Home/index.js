import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { logout, selectLoginStatus, selectUser } from './../../_slices/userSlice';

const Home = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const loginStatus = useSelector(selectLoginStatus);
  const nickname = useSelector(selectUser)?.nickname;

  const goLogin = () => {
    navigate(`/login`);
  }

  const logOut = () => {
    dispatch(logout());
  }

  const goSignup = () => {
    navigate(`/signup`);
  }

  const goWrite = () => {
    navigate(`/${nickname}/posts/write`);
  }

  useEffect(()=>{
    if(loginStatus == 'idle'){
      alert('로그아웃 성공')
    }
  },[loginStatus]);

  return (
    <>
    <div>Home: Bot Line Blog</div>
    <button onClick={goLogin}>Login</button>
    <button onClick={logOut}>Logout</button>
    <button onClick={goSignup}>SignUp</button>
    <button onClick={goWrite}>Write</button>
    </>
  )
}

export default Home;