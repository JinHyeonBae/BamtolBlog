import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { Form, Input, Button, Checkbox } from 'antd';
import { MailOutlined, LockOutlined, LockFilled, UserOutlined } from '@ant-design/icons';
//import CryptoJS from 'crypto-js';
import { signup, setSignupStatusIdle,
  selectSignupStatus, selectStatusCode } from '../../_slices/userSlice';
import Layout from '../../utils/layout';

const SignUp = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [form] = Form.useForm();
  
  const signupStatus = useSelector(selectSignupStatus);
  const statusCode = useSelector(selectStatusCode);
  
  // const EmailRegex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  const PasswordRegex = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+])(?!.*[^a-zA-z0-9$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;

  useEffect(() => {
    if(statusCode === '40901' || statusCode === '40903'){
      form.setFields([{
          name: 'email',
          errors: ['이미 사용 중인 이메일입니다.'],
      }]);
    }
    if(statusCode === '40902' || statusCode === '40903'){
      form.setFields([{
          name: 'nickname',
          errors: ['이미 사용 중인 닉네임 입니다.'],
      }]);
    }
  }, [statusCode]);

  useEffect(()=>{
    if(signupStatus === 'success' && statusCode === '201'){
      alert('회원가입 성공');
      dispatch(setSignupStatusIdle());
      navigate(`/`);
    } else if(signupStatus === 'failed'){
      alert('회원가입 실패. 다시 시도해주세요.');
      dispatch(setSignupStatusIdle());
    }
  },[signupStatus, statusCode]);
  
  const onSubmit = (data) => {
    let body = data;
    //body.password = CryptoJS.AES.encrypt(body.password, process.env.REACT_APP_SECRET_KEY).toString();
    dispatch(signup(body));
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

  return(
    <Layout>
      <Form
        form={form}
        {...formItemLayout}
        onFinish={onSubmit}
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
            {pattern: PasswordRegex, message: '비밀번호는 숫자, 영어, 특수문자를 포함하여 8~16자리로 설정해주세요.' },
          ]}
        >
          <Input type='password' prefix={<LockOutlined id='password' placeholder='Password'/>}/>
        </Form.Item>
        
        <Form.Item
          label='Password Confirm'
          name='passwordConfirm'
          dependencies={['password']}
          rules={[
            {required: true, message: '확인용 비밀번호를 입력해주세요.' },
            ({ getFieldValue }) => ({
              validator(_, value) {
                if (!value || getFieldValue('password') === value) {
                  return Promise.resolve();
                }
                return Promise.reject(new Error('비밀번호가 일치하지 않습니다.'));
              },
            })
          ]}
        >
          <Input type='password' prefix={<LockFilled id='passwordConfirm' placeholder='Password Confirm'/>}/>
        </Form.Item>
        
        <Form.Item
          label='Nickname'
          name='nickname'
          validateTrigger='onBlur'
          rules={[
            {required: true, message: '닉네임을 입력해주세요.' }
          ]}
        >
          <Input prefix={<UserOutlined id='nickname' placeholder='Nickname'/>}/>
        </Form.Item>

        <Form.Item
          name='privacyPolicy'
          wrapperCol={{ ...formItemLayout.wrapperCol, offset: 6 }}
          valuePropName='checked'
          rules={[
            {required: true, message: '개인정보 수집에 동의해주세요.' }
          ]}
        >
          <Checkbox id='privacyPolicy'>개인정보 수집 동의</Checkbox>
        </Form.Item>

        <Form.Item
          name='signUpButton'
          wrapperCol={{ ...formItemLayout.wrapperCol, offset: 6 }}
          >
          <Button type='primary' htmlType='submit'>
            회원 가입
          </Button>
        </Form.Item>
      </Form>
    </Layout>
  )
}
export default SignUp;