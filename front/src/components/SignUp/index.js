import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from "react-redux";
import useInput from "../../hooks/useInput";
import { signup, checkEmailDuplicate, checkNicknameDuplicate,
  setSignupStatusIdle, setEmailDuplicateStatusIdle, setNicknameDuplicateStatusIdle,
  selectEmailNotDuplication, selectNicknameNotDuplication,
  selectSignupStatus, selectEmailDuplicateStatus, selectNicknameDuplicateStatus } from "../../_slices/userSlice";

const SignUp = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const signupStatus = useSelector(selectSignupStatus);
  const emailDuplicateStatus = useSelector(selectEmailDuplicateStatus);
  const nicknameDuplicateStatus = useSelector(selectNicknameDuplicateStatus);
  const emailNotDuplication = useSelector(selectEmailNotDuplication);
  const nicknameNotDuplication = useSelector(selectNicknameNotDuplication);

  const EmailRegex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  const PasswordRegex = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+])(?!.*[^a-zA-z0-9$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;

  const [Email, setEmail] = useState('');
  const [Password, setPassword] = useState('');
  const [PasswordConfirm, onChangePasswordConfirm] = useInput('');
  const [Nickname, setNickname] = useState('');
  const [AgreePrivacyPolicy, setAgreePrivacyPolicy] = useState(false);

  const [EmailEmptyError, setEmailEmptyError] = useState(false);
  const [EmailValidError, setEmailValidError] = useState(false);
  const [EmailDuplicationError, setEmailDuplicationError] = useState(false);
  const [PasswordEmptyError, setPasswordEmptyError] = useState(false);
  const [PasswordValidError, setPasswordValidError] = useState(false);
  const [PasswordConfirmError, setPasswordConfirmError] = useState(false);
  const [NicknameEmptyError, setNicknameEmptyError] = useState(false);
  const [NicknameDuplicationError, setNicknameDuplicationError] = useState(false);
  const [AgreePrivacyPolicyError, setAgreePrivacyPolicyError] = useState(false);

  useEffect(()=>{
    dispatch(setSignupStatusIdle());
  },[]);

  useEffect(()=>{
    if(Email === '' || emailDuplicateStatus !== 'success') return;
    if(emailNotDuplication === false){
      alert("중복된 이메일 입니다.");
    } else if(emailNotDuplication === true){
      alert("사용 가능한 이메일 입니다.")
    }
  },[emailDuplicateStatus, emailNotDuplication]);
  
  useEffect(()=>{
    if(signupStatus === 'success'){
      alert("회원가입 성공");
      navigate(`/`);
    }
  },[signupStatus]);

  const checkEmailDuplication = () => {
    if(EmailValidError === true) return; 
    dispatch(checkEmailDuplicate(Email));
  }

  const onChangeEmail = (e) => {
    setEmail(e.target.value);
    if(EmailRegex.test(Email) === false){
      setEmailValidError(true);
    } else {
      setEmailValidError(false);
    }
    dispatch(setEmailDuplicateStatusIdle());
  }

  const onChangePassword = (e) => {
    setPassword(e.target.value);
    if(PasswordRegex.test(e.target.value) === false){
      setPasswordValidError(true);
    } else {
      setPasswordValidError(false);
    }
  }

  const onChangeNickname = (e) => {
    setNickname(e.target.value);
    dispatch(setNicknameDuplicateStatusIdle());
    dispatch(checkNicknameDuplicate(e.target.value));
  }

  const onChangeAgreePrivacyPolicy = (e) => {
    setAgreePrivacyPolicy(e.target.checked);
  }

  const checkValidInput = () => {
    if(Email === ''){
      setEmailEmptyError(true);
    } else {
      setEmailEmptyError(false);
    }

    if(EmailRegex.test(Email) === false){
      setEmailValidError(true);
    } else {
      setEmailValidError(false);
    }

    if(emailDuplicateStatus === 'idle'){
      setEmailDuplicationError(true);
    } else {
      setEmailDuplicationError(false);
    }

    if(Password === ''){
      setPasswordEmptyError(true);
    } else {
      setPasswordEmptyError(false);
    }

    if(Password != PasswordConfirm) {
      setPasswordConfirmError(true);
    } else {
      setPasswordConfirmError(false);
    }
    
    if(Nickname === ''){
      setNicknameEmptyError(true);
    } else {
      setNicknameEmptyError(false);
    }

    if(nicknameDuplicateStatus !== 'success'){
      setNicknameDuplicationError(true);
    } else {
      setNicknameDuplicationError(false);
    }

    if(AgreePrivacyPolicy === false){
      setAgreePrivacyPolicyError(true);
    } else {
      setAgreePrivacyPolicyError(false);
    }
  }

  const onSubmitSignUpForm = (e) => {
    e.preventDefault();
    //체크모두 한 후에 아래 if 문 실행
    checkValidInput();

    if(EmailEmptyError
      || EmailValidError
      || EmailDuplicationError
      || PasswordEmptyError 
      || PasswordValidError 
      || PasswordConfirmError 
      || NicknameEmptyError
      || NicknameDuplicationError
      || AgreePrivacyPolicyError){
      return ;
    }

    //암호화
    const body = {
      email: Email,
      password: Password,
      nickname: Nickname
    }
    dispatch(signup(body));
  }

  return(
    <div>
      <form onSubmit={onSubmitSignUpForm}>
        <div>
          <label htmlFor="email">Email</label>
          <input id="email" value={Email} onChange={onChangeEmail} />
          <button type="button" onClick={checkEmailDuplication}>이메일 중복 검사</button>
          <div className="ErrorMessage">
            {EmailEmptyError && <span>이메일을 입력해주세요.</span>}
            {EmailValidError && <span>올바른 이메일 형식을 입력해주세요.</span>}
            {EmailDuplicationError && <span>이메일 중복 검사를 눌러주세요.</span>}
          </div>
        </div>
        <div>
          <label htmlFor="password">Password</label>
          <input id="password" type="password" value={Password} onChange={onChangePassword} autoComplete="off" />
          <div className="ErrorMessage">
            {PasswordEmptyError && <span>비밀번호를 입력해주세요.</span>}
            {PasswordValidError && <span>비밀번호는 숫자, 영어, 특수문자를 포함하여 8~16자리로 설정해주세요.</span>}
          </div>
        </div>
        <div>
          <label htmlFor="passwordConfirm">Password Confirm</label>
          <input id="passwordConfirm" type="password" value={PasswordConfirm} onChange={onChangePasswordConfirm} autoComplete="off" />
          <div className="ErrorMessage">
            {PasswordConfirmError && <span>비밀번호가 일치하지 않습니다.</span>}
          </div>
        </div>
        <div>
          <label htmlFor="nickname">Nickname</label>
          <input id="nickname" value={Nickname} onChange={onChangeNickname} />
          <div className="ErrorMessage">
            {NicknameEmptyError === true && <span>닉네임을 입력해주세요.</span>}
            {nicknameDuplicateStatus === 'success' && nicknameNotDuplication === true  && <span>사용 가능한 닉네임 입니다.</span> }
            {nicknameNotDuplication === false && <span>중복된 닉네임 입니다.</span>}
          </div>
        </div>
        <div>
          <label htmlFor="privacyPolicy">AgreePrivacyPolicy</label>
          <input id="privacyPolicy" type="checkbox" checked={AgreePrivacyPolicy} onChange={onChangeAgreePrivacyPolicy} />
          <div className="ErrorMessage">
            {AgreePrivacyPolicyError && <span>개인정보 수집에 동의해주세요.</span>}
          </div>
        </div>
        <div>
          <button type="submit">Submit</button>
        </div>
      </form>
    </div>
  )
}
export default SignUp;