import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { useForm } from 'react-hook-form';
//import CryptoJS from 'crypto-js';
import { login, selectLoginStatus, selectStatusCode, selectError } from './../../_slices/userSlice';

const Login = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const loginStatus = useSelector(selectLoginStatus);
  const statusCode = useSelector(selectStatusCode);
  const error = useSelector(selectError);
  let emailNotFound = statusCode === '40101';
  let passwordNotFound = statusCode === '40102';
  
  const { register, watch, handleSubmit, formState: { errors } } = useForm();

  const EmailRegex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

  const watchEmail = watch('email');
  const watchPassword = watch('password'); 

  useEffect(()=>{
    console.log(emailNotFound, passwordNotFound);
  },[])

  useEffect(()=>{
    emailNotFound = false;
    passwordNotFound = false; 
  },[watchEmail, watchPassword]);

  useEffect(()=>{
    if(loginStatus === 'success' && statusCode === '200'){
      navigate(`/`);
    } 
  }, [loginStatus])

  const logIn = (data) => {
    let body = data;
    //body.password = CryptoJS.AES.encrypt(body.password, process.env.REACT_APP_SECRET_KEY).toString();
    dispatch(login(body));
  }

  return (
    <form onSubmit={handleSubmit(logIn)}>
      <div>
          <label htmlFor='email'>Email :</label>
          <input id='email' placeholder='Email' 
            {...register('email', {
              required: '이메일을 입력해주세요.',
              pattern: {
                value: EmailRegex,
                message: '올바른 이메일 형식을 입력해주세요.',
              },
              validate: () => !emailNotFound || '존재하지 않는 이메일입니다.',
            })} />
          <div className='ErrorMessage'>
            {errors.email && <span>{errors.email.message}</span>}
          </div>
        </div>
        <div>
          <label htmlFor='password'>Password :</label>
          <input id='password' type='password' placeholder='Password' autoComplete='off' 
            {...register('password', {
              required: '비밀번호를 입력해주세요.',
              validate: () => !passwordNotFound || '잘못된 비밀번호입니다.',
            })} />
          <div className='ErrorMessage'>
            {errors.password && <span>{errors.password.message}</span>}
          </div>
        </div>
      <button>login</button>
      {loginStatus === 'failed' && <div className="ErrorMessage">{error}</div>}
    </form>
  )
}

export default Login;