import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { selectUser } from '../../_slices/userSlice';
import { selectPostsList, savePostData, changePostViewMode } from '../../_slices/postSlice';
import { Layout as LayoutAntd, Menu, Input, Button, Form } from 'antd';
import { PlusSquareOutlined, PlusSquareFilled,  CheckOutlined } from '@ant-design/icons';
import styled from 'styled-components';
import './SideBar.scss';

const { Sider } = LayoutAntd;

const StyledInputSearch = styled(Input.Search)`
  vertical-align: middle;
  padding: 10px 5px;
  background: white;
`;

const SideBar = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const nickname = useSelector(selectUser)?.nickname;
  const postsListData = useSelector(selectPostsList);

  const [postsList, setPostsList] = useState([]);
  const [additionClicked, setAdditionClicked] = useState(false);
  const [newCategoryName, setNewCategoryName] = useState('');

  const getInitialPostsList = () => {
    return new Promise((resolve, reject) => {
        let PostsList = postsListData.map((category) => {
          let childrenList = category.children.map(list => ({
            key: `${category.id}-${list.id}`,
            label: list.title
          }))
    
          childrenList = [...childrenList, {
            key: `${category}-Plus`,
            label: (
              <div style={{textAlign: 'left'}}>
                <PlusSquareOutlined 
                onClick={()=>{
                  dispatch(savePostData({ title: null, contents: null, displayLevel:'public', price: 0 }));
                  dispatch(changePostViewMode('modify'));
                  navigate(`/${nickname}/posts/${category.id}/write`)
                }}
                style={{fontSize: "15px"}}/>
              </div>)
          }]
    
          return ({
            key: `${category.id}`,
            label: category.title,
            children: childrenList
          })
        })
        if(PostsList){
          resolve(PostsList);
        } else {
          reject(new Error(`Error(SideBar): Can't get posts list`));
        }
    })
  }
  
  async function setInitialPostsList() {
    const initialPostsList = await getInitialPostsList();
    setPostsList(initialPostsList);
  }

  useEffect(()=>{
    setInitialPostsList();
  },[]);

  const makeNewCategory = () => {
    //Post API로 서버에 카테고리 추가
    setNewCategoryName('HELLO WORLD');
    //현재 카테고리에 추가
    setAdditionClicked(false);
  }

  return (
      <Sider width={200} 
        className="site-layout-background" 
        breakpoint="lg"
        collapsedWidth="0"
        zeroWidthTriggerStyle={{'top': 0}} 
      >
        <div>
          <StyledInputSearch placeholder="search" allowClear />
        </div>
        <Menu
          mode="inline"
          defaultSelectedKeys={['Web']}
          defaultOpenKeys={['Web']}
          style={{
            borderRight: 0,
          }}
          items={postsList}
        />
        { additionClicked === true
        ?  <Form 
            onFinish={makeNewCategory} 
            style={{'padding': '10px 20px'}} >
            <Form.Item>
              <Input.Group compact>
                <Input style={{'width': '80%'}} value={newCategoryName} />
                <Button style={{'width': '20%'}} icon={<CheckOutlined />} htmlType='submit' />
              </Input.Group>
            </Form.Item>
          </Form>
       :  <Button 
          type='link'
          onClick={()=>setAdditionClicked(true)}
          style={{width: "100%"}}>
          <PlusSquareFilled  style={{fontSize: "25px"}}/>
        </Button>
      }
      </Sider>
  );
}

export default SideBar;
