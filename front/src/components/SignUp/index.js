import React, { useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from "react-redux";
import { useForm } from 'react-hook-form';
//import CryptoJS from 'crypto-js';
import { signup, setSignupStatusIdle,
  selectSignupStatus, selectStatusCode } from "../../_slices/userSlice";

const SignUp = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const { register, watch, trigger, getValues, handleSubmit, formState: { errors } } = useForm();
  
  const signupStatus = useSelector(selectSignupStatus);
  const statusCode = useSelector(selectStatusCode);
  let emailDuplication = statusCode === '40901' || statusCode === '40903'; 
  let nicknameDuplication = statusCode === '40902' || statusCode === '40903'; 
  
  const EmailRegex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  const PasswordRegex = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+])(?!.*[^a-zA-z0-9$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;

  const watchEmail = watch('email');
  const watchNickname = watch('nickname');

  useEffect(()=>{
    console.log(`emailDup: ${emailDuplication}, nicknameDup: ${nicknameDuplication}`);
  },[emailDuplication, nicknameDuplication])

  useEffect(() => {
    emailDuplication = false;
    console.log(watchEmail, emailDuplication);
    trigger('email');
  },[watchEmail]);

  useEffect(()=>{
    if(signupStatus === 'success'){
      nicknameDuplication = false;
      trigger('nickname');
      console.log(watchNickname, nicknameDuplication);
    }
  },[watchNickname]);

  useEffect(() => {
    console.log(`update! emailDup: ${emailDuplication}, nicknameDup: ${nicknameDuplication}`);
    dispatch(setSignupStatusIdle());
  },[]);

  useEffect(()=>{
    if(signupStatus === 'success' && !emailDuplication && !nicknameDuplication){
      alert("회원가입 성공");
      dispatch(setSignupStatusIdle());
      navigate(`/`);
    } else if(signupStatus === 'failed'){
      alert("회원가입 실패. 다시 시도해주세요.");
      dispatch(setSignupStatusIdle());
    }
  },[signupStatus, emailDuplication, nicknameDuplication]);
  
  const onSubmit = (data) => {
    let body = data;
    //body.password = CryptoJS.AES.encrypt(body.password, process.env.REACT_APP_SECRET_KEY).toString();
    dispatch(signup(body));
  }

  return(
    <div>
      <form onSubmit={handleSubmit(onSubmit)}>
        <div>
          <label htmlFor='email'>Email</label>
          <input id='email' placeholder='Email' 
            {...register('email', {
              required: '이메일을 입력해주세요.',
              pattern: {
                value: EmailRegex,
                message: '올바른 이메일 형식을 입력해주세요.',
              },
              validate: () => !emailDuplication || '이미 사용 중인 이메일입니다.',
            })} />
          <div className='ErrorMessage'>
            {errors.email && <span>{errors.email.message}</span>}
          </div>
        </div>
        <div>
          <label htmlFor='password'>Password</label>
          <input id='password' type='password' placeholder='Password' autoComplete='off' 
            {...register('password', {
              required: '비밀번호를 입력해주세요.',
              pattern: {
                value: PasswordRegex,
                message: '비밀번호는 숫자, 영어, 특수문자를 포함하여 8~16자리로 설정해주세요.',
              } 
            })} />
          <div className='ErrorMessage'>
            {errors.password && <span>{errors.password.message}</span>}
          </div>
        </div>
        <div>
          <label htmlFor='passwordConfirm'>Password Confirm</label>
          <input id='passwordConfirm' type='password' placeholder='Password Confirm' autoComplete='off'
            {...register('passwordConfirm', {
              required: '확인용 비밀번호 입력해주세요.',
              validate: value => value === getValues("password") || '비밀번호가 일치하지 않습니다.'
            })} />
          <div className='ErrorMessage'>
            {errors.passwordConfirm && <span>{errors.passwordConfirm.message}</span>}
          </div>
        </div>
        <div>
          <label htmlFor='nickname'>Nickname</label>
          <input id='nickname' placeholder='Nickname' 
            {...register('nickname', {
              required: '닉네임을 입력해주세요.',
              validate: () => !nicknameDuplication || '중복된 닉네임 입니다.',
            })} />
          <div className='ErrorMessage'>
            {errors.nickname && <span>{errors.nickname.message}</span>}
          </div>
        </div>
        <div>
          <input id='privacyPolicy' type='checkbox' 
            {...register('privacyPolicy', {
              required: '개인정보 수집에 동의해주세요.',
            })}/>
          <label htmlFor='privacyPolicy'>개인정보 수집 동의</label>
          <div className='ErrorMessage'>
            {errors.privacyPolicy && <span>{errors.privacyPolicy.message}</span>}
          </div>
        </div>
        <input type='submit' />
      </form>
    </div>
  )
}
export default SignUp;