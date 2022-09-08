import React, { useEffect, useCallback } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams } from 'react-router';
import { useNavigate } from 'react-router-dom';
import { changePostViewMode, savePostData, savePost, selectModifyingPostData, selectSavePostDataStatus, selectCurrPostId, selectStatusCode } from '../../_slices/postSlice';
import { selectUser } from '../../_slices/userSlice';
import { Form, Button, Radio, InputNumber } from 'antd';

const PostSave = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { userNickname, categoryId } = useParams();

  const ModifyingPost = useSelector(selectModifyingPostData);
  const savePostDataStatus = useSelector(selectSavePostDataStatus);
  const currPostId = useSelector(selectCurrPostId);
  const userId = useSelector(selectUser).id;
  const statusCode = useSelector(selectStatusCode);

  const [form] = Form.useForm();
  
  useEffect(()=>{
    if (savePostDataStatus === 'success'){
      if(statusCode == '200') {
        alert("성공");
        navigate(`/${userNickname}/posts/${currPostId}`);
      } else {
        alert("문제 발생! / 이미 등록된 게시글입니다.");
      }
    } else if (savePostDataStatus === 'failed') {
      alert("저장에 실패했습니다. 다시 시도해주세요.");
    }
  },[savePostDataStatus])

  const changeViewMode = useCallback((mode)=>{
    dispatch(savePostData({
      title: ModifyingPost.title,
      contents: ModifyingPost.contents,
      displayLevel: form.getFieldValue('displayLevel'),
      price: form.getFieldValue('price')
    }));
    dispatch(changePostViewMode(mode))
  }, [])
  
  const onSubmitHandler = (data) => {
    const body = {
      categoryId: categoryId,
      title: ModifyingPost.title,
      displayLevel: data.displayLevel,
      price: data.price,
      userId: userId,
      contents: ModifyingPost.contents
    }
    dispatch(savePost(body));
  }

  return (
    <div className='PostSave'>
      <Form 
        form={form}
        onFinish={onSubmitHandler}
        initialValues={{
          displayLevel: ModifyingPost.displayLevel,
          price: ModifyingPost.price
        }}
      >
        <div className='topButtons'>
          <Button className='leftButton' type='primary' htmlType='submit'>등록</Button>
          <Button className='rightButton' onClick={()=>changeViewMode('modify')}>다시 수정</Button>
        </div>
        <Form.Item label='공개여부' name='displayLevel'>
          <Radio.Group>
            <Radio.Button value='public'>Public</Radio.Button>
            <Radio.Button value='protect'>Protect</Radio.Button>
            <Radio.Button value='private'>Private</Radio.Button>
          </Radio.Group>
        </Form.Item>
        <Form.Item label='구매 비용' name='price'>
        <InputNumber prefix="￦" style={{ width: '100%'}} />
        </Form.Item>
      </Form>
    </div>
  )
}

export default PostSave;