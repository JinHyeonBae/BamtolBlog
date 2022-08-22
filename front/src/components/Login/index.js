import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { Form, Input, Button, Space } from 'antd';
import { MailOutlined, LockOutlined } from '@ant-design/icons';
//import CryptoJS from 'crypto-js';
import { login, selectLoginStatus, selectStatusCode, selectError } from './../../_slices/userSlice';
import Layout from '../../utils/layout';

const Login = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [form] = Form.useForm();

  const loginStatus = useSelector(selectLoginStatus);
  const statusCode = useSelector(selectStatusCode);
  const error = useSelector(selectError);

  useEffect(() => {
    if(statusCode === '40101'){
      form.setFields([{
          name: 'email',
          errors: ['존재하지 않는 이메일입니다.'],
      }]);
    } else if(statusCode === '40102'){
      form.setFields([{
          name: 'password',
          errors: ['잘못된 비밀번호입니다.'],
      }]);
    } else if(statusCode !== '' && statusCode !== '200'){
      form.setFields([{
          name: 'buttons',
          errors: ['다시 시도해주세요.'],
      }]);
    }
  }, [statusCode]);

  // const EmailRegex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

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

  const formItemLayout = {
    labelCol: {
      xs: { span: 24 },
      sm: { span: 6 },
    },
    wrapperCol: {
      xs: { span: 24 },
      sm: { span: 14 },
    },
  };

  return (
    <Layout>
      <Form
        form={form}
        {...formItemLayout}
        onFinish={logIn}
        autoComplete='off'
      >
        <Form.Item
          label='Email'
          name='email'
          validateTrigger='onBlur'
          rules={[
            {required: true, message: '이메일을 입력해주세요.' },
            {type: 'email', message: '올바른 이메일 형식을 입력해주세요.' },
          ]}
        >
          <Input prefix={<MailOutlined id='email' placeholder='Email'/>}/>
        </Form.Item>
        
        <Form.Item
          label='Password'
          name='password'
          validateTrigger='onBlur'
          rules={[
            {required: true, message: '비밀번호를 입력해주세요.' },
          ]}
        >
          <Input type='password' prefix={<LockOutlined id='password' placeholder='Password'/>}/>
        </Form.Item>

        <Form.Item
          name='buttons'
          wrapperCol={{ ...formItemLayout.wrapperCol, offset: 6 }}
        >
          <Space >
          <Button type='primary' htmlType='submit'>
            로그인
          </Button>
          <Button type='link' onClick={()=>{navigate(`/signup`)}}>회원가입</Button>
          </Space>
        </Form.Item>
        {loginStatus === 'failed' && <div className='ErrorMessage'>{error}</div>}
      </Form>
    </Layout>
  )
}

export default Login;